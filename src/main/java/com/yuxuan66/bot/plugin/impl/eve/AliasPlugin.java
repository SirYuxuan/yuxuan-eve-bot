package com.yuxuan66.bot.plugin.impl.eve;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxuan66.bot.ReConst;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.definition.MessageScope;
import com.yuxuan66.bot.plugin.definition.PluginInfo;
import com.yuxuan66.bot.plugin.impl.eve.entity.TypeAlias;
import com.yuxuan66.bot.plugin.impl.eve.mapper.TypeAliasMapper;
import com.yuxuan66.support.cache.key.CacheKey;
import com.yuxuan66.support.cache.redis.RedisKit;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 别名设置插件
 *
 * @author Sir丶雨轩
 * @since 2023/8/24
 */
@Component
@RequiredArgsConstructor
public class AliasPlugin implements MessagePlugin {

    @Resource
    private TypeAliasMapper typeAliasMapper;
    private final RedisKit redis;

    @Override
    public PluginInfo info() {
        return new PluginInfo("别名设置", 1, MessageScope.GROUP);
    }

    @Override
    public String match() {
        return ReConst.ALIAS;
    }

    @Override
    public boolean apply(MessageEvent event, Matcher matcher, String message) {

        // 发送人QQ
        long qq = event.getSender().getId();
        // 名称 如YST
        String name = matcher.group(2);
        // 别名 如伊什塔级
        String alias = matcher.group(3);
        // 1. 判断是否是管理员，管理员可以设置全局的
        boolean isManager = ((GroupMessageEvent) event).getPermission().getLevel() > MemberPermission.MEMBER.getLevel();
        // 判断当前关键字是否存在
        List<TypeAlias> aliasList = typeAliasMapper.selectList(new QueryWrapper<TypeAlias>().eq("name", name).eq(!isManager, "qq", qq).isNull(isManager,"qq"));
        if (aliasList.isEmpty()) {
            // 重新插入
            TypeAlias typeAlias = new TypeAlias();
            typeAlias.setName(name);
            typeAlias.setAlias(alias);
            typeAlias.setQq(isManager ? null : qq);
            typeAlias.insert();
        } else {
            // 更新原始数据
            TypeAlias typeAlias = aliasList.get(0);
            typeAlias.setAlias(alias);
            typeAlias.updateById();
        }
        // 更新缓存
        String cacheKey = isManager
                ? CacheKey.EVE_TYPE_ALIAS
                : CacheKey.EVE_TYPE_ALIAS + ":" + qq;
        redis.hSet(cacheKey,name,alias);
        sendMessage(event,"别名设置完成，["+name+"]===>["+alias+"] \n" + (isManager?"您是管理员,设置为全局生效":"您是普通成员,设置本人生效"));
        return false;
    }

}
