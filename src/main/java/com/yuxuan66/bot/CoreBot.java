package com.yuxuan66.bot;

import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yuxuan66.bot.listener.EveBotMessageEventListener;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.impl.eve.entity.Type;
import com.yuxuan66.bot.plugin.impl.eve.mapper.TypeAliasMapper;
import com.yuxuan66.bot.plugin.impl.eve.mapper.TypeMapper;
import com.yuxuan66.support.cache.key.CacheKey;
import com.yuxuan66.support.cache.redis.RedisKit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * CoreBot类用于创建和初始化机器人实例，并提供访问机器人实例的方法。<br>
 * 通过Lombok的@Getter注解生成bot字段的getter方法。<br>
 * 该类被标记为@Component，表示它是一个Spring组件，会被自动扫描和装配。<br>
 * 通过@PostConstruct注解标记的initBot方法在对象初始化后自动调用，用于创建和登录机器人。<br>
 * 机器人的登录使用了Mirai框架提供的BotFactory和BotAuthorization类。<br>
 *
 * @since 2023/8/24
 */
@Getter
@Component
@RequiredArgsConstructor
public class CoreBot {

    /**
     * 原生Bot对象
     */
    private Bot bot;

    /**
     * 用于操作数据库中的物品类型
     */
    @Resource
    private TypeMapper typeMapper;

    /**
     * 用于操作数据库中的物品类型的别名
     */
    @Resource
    private TypeAliasMapper typeAliasMapper;

    /**
     * RedisKit用于进行缓存操作
     */
    private final RedisKit redis;

    /**
     * 消息插件列表
     */
    public static final List<MessagePlugin> messagePluginList = new ArrayList<>();

    /**
     * 系统内所有物品列表
     */
    public static final List<Type> typeList = new ArrayList<>();

    /**
     * 消息事件监听器
     */
    private final EveBotMessageEventListener eveBotMessageEventListener;

    /**
     * 初始化机器人<br>
     * 通过调用BotFactory的newBot方法创建机器人实例，并设置相关配置。<br>
     * 使用BotAuthorization的byQRCode方法进行身份验证。<br>
     * 设置协议为MiraiProtocol.ANDROID_WATCH，设备信息采用fileBasedDeviceInfo。<br>
     * 调用机器人的login方法进行登录。<br>
     */
    @PostConstruct
    public void initBot() {
        bot = BotFactory.INSTANCE.newBot(2438372649L, BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.fileBasedDeviceInfo();
        });

        // 添加消息执行插件
        messagePluginList.addAll(SpringUtil.getBeansOfType(MessagePlugin.class).values().stream().toList());

        // 注册消息事件监听
        bot.getEventChannel().subscribeAlways(MessageEvent.class, eveBotMessageEventListener);

        // 加载数据库内所有可市场交易的物品
        typeList.addAll(typeMapper.selectList(null).stream()
                .filter(item -> item.getMarketGroupId() != null)
                .toList());

        // 为物品设置拼音
        typeList.forEach(type -> {
            type.setPinyin(PinyinUtil.getPinyin(type.getName()).replace(" ",""));
        });

        // 写入别名缓存
        typeAliasMapper.selectList(null).forEach(typeAlias -> {
            String cacheKey = typeAlias.getQq() == null
                    ? CacheKey.EVE_TYPE_ALIAS
                    : CacheKey.EVE_TYPE_ALIAS + ":" + typeAlias.getQq();
            redis.hSet(cacheKey, typeAlias.getName().toLowerCase(), typeAlias.getAlias());
        });

        // 登陆机器人
        bot.login();
    }
}
