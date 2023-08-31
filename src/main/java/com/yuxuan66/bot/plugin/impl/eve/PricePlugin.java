package com.yuxuan66.bot.plugin.impl.eve;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.yuxuan66.bot.CoreBot;
import com.yuxuan66.bot.ReConst;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.impl.eve.entity.PriceBean;
import com.yuxuan66.bot.plugin.impl.eve.entity.Type;
import com.yuxuan66.bot.utils.Lang;
import com.yuxuan66.support.cache.key.CacheKey;
import com.yuxuan66.support.cache.redis.RedisKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * PricePlugin类实现了MessagePlugin接口，用于处理EVE市场价格查询的消息。
 * 当消息满足匹配条件时，将调用apply方法进行处理。
 *
 * @since 2023/8/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PricePlugin implements MessagePlugin {

    /**
     * RedisKit实例，用于缓存操作
     */
    private final RedisKit redis;


    /**
     * 返回用于匹配的正则表达式
     *
     * @return 匹配的正则表达式
     */
    @Override
    public String match() {
        return ReConst.PRICE;
    }

    /**
     * 处理消息事件的方法，当匹配成功时会调用此方法来处理消息
     *
     * @param event   消息事件
     * @param matcher 正则表达式匹配器
     * @param message 匹配到的消息内容
     * @return 是否继续处理后续插件
     */
    @Override
    public boolean apply(MessageEvent event, Matcher matcher, String message) {
        // 匹配是否是价格查询
        String instructions = matcher.group(1).trim();
        String name = matcher.group(2).trim().toLowerCase();

        if (StrUtil.isBlank(name)) {
            return true;
        }

        // 是否是欧服市场查询
        boolean isEur = !instructions.contains("g");
        // 是否查询套装价格
        boolean isCol = instructions.contains("col");

        // 开始价格查询流程
        // 1. 开始尝试获取别名 这是仅从redis中获取, 用户在添加别名或系统启动时刷新缓存
        final String realName = getRealName(name, event.getSender().getId());
        final String pinyinName = PinyinUtil.getPinyin(realName).replace(" ", "");

        // 2. 过滤出有效的物品列表
        boolean isSkin = name.contains("皮肤") || name.contains("涂装");
        boolean isBluePrint = name.contains("蓝图") || name.contains("配方");

        // 2.1 过滤有效的TypeList 蓝图/皮肤 过滤
        List<Type> validList = CoreBot.typeList.stream().filter(type -> checkValidType(isSkin, isBluePrint, type)).toList();
        // 2.2 在有效的基础上过滤名称正确的TypeList
        List<Type> typeList = validList.stream().filter(type -> checkNameType(realName, type)).limit(10).toList();
        // 2.3 如果没有匹配到物品/尝试使用拼音查询
        if (typeList.isEmpty()) {
            typeList = validList.stream().filter(type -> type.getPinyin().contains(pinyinName)).limit(10).toList();
        }

        // 2.4 尽最大努力仍无法匹配到用户输入的名称
        if (typeList.isEmpty()) {
            sendMessage(event, "你在查个嘚儿");
            return false;
        }

        // 3. 开始获取价格
        Map<Integer, PriceBean> priceBeanMap = getPrice(isEur, typeList.stream().map(Type::getId).toList());

        long sellAll = 0, buyAll = 0;

        StringBuilder result = new StringBuilder();
        // 5. 开始拼装数据
        for (Type type : typeList) {
            PriceBean priceBean = priceBeanMap.get(type.getId());
            sellAll += priceBean.getSell();
            buyAll += priceBean.getBuy();
            result.append(getName(type));
            result.append(getPriceVal(priceBean, 1));
            if ("伊甸币".equals(type.getName())) {
                result.append(getName(type).replace("\r", "")).append("*500\r").append(getPriceVal(priceBean, 500));
            }
        }
        if (isCol) {
            result.append(name).append("全套价格\r");
            result.append(getPriceVal(buyAll, sellAll));
        }
        sendMessage(event, result.toString());
        return false;
    }


    /**
     * 获取价格的输出
     *
     * @param priceBean 价格
     * @return 输出
     */
    public String getPriceVal(PriceBean priceBean, int multiple) {
        return "收单: " + Lang.getFormatNumber(priceBean.getBuy() * multiple) + " ISK \r中间: " + Lang.getFormatNumber((priceBean.getSell() + priceBean.getBuy()) / 2 * multiple) + " ISK\r卖单: " + Lang.getFormatNumber(priceBean.getSell() * multiple) + " ISK\r";
    }

    /**
     * 获取价格的输出
     *
     * @return 输出
     */
    public String getPriceVal(long buy, long sell) {
        return getPriceVal(new PriceBean(0, buy, sell), 1);
    }

    /**
     * 获取名字的格式化输出
     *
     * @param type 物品
     * @return 格式化输出
     */
    public String getName(Type type) {
        return type.getName() + "\r";
    }

    /**
     * 获取物品价格信息
     *
     * @param isEur      是否是欧服市场
     * @param typeIdList 物品类型ID列表
     * @return 物品类型ID到价格信息的映射
     */
    public Map<Integer, PriceBean> getPrice(boolean isEur, List<Integer> typeIdList) {
        // 构建基础URL
        StringBuilder basePath = new StringBuilder("http://www.ceve-market.org/" + (isEur ? "tq" : "") + "api/marketstat?usesystem=30000142");

        // 添加物品类型ID参数
        typeIdList.forEach(typeId -> basePath.append("&typeid=").append(typeId));

        // 发起HTTP请求
        HttpRequest request = HttpUtil.createGet(basePath.toString());
        String xmlData = request.execute().body();

        // 解析XML数据
        NodeList nodeList = XmlUtil.parseXml(xmlData).getDocumentElement().getElementsByTagName("marketstat").item(0).getChildNodes();

        // 构建物品类型ID到价格信息的映射
        Map<Integer, PriceBean> result = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            int typeId = Convert.toInt(item.getAttributes().getNamedItem("id").getNodeValue());
            Node buyNode = item.getChildNodes().item(0);
            Node sellNode = item.getChildNodes().item(1);
            result.put(typeId, new PriceBean(typeId, Convert.toLong(buyNode.getChildNodes().item(2).getTextContent()), Convert.toLong(sellNode.getChildNodes().item(3).getTextContent())));
        }
        return result;
    }


    /**
     * 判断物品是否有效
     *
     * @param isSkin      是否是涂装
     * @param isBluePrint 是否是蓝图
     * @param type        物品
     * @return 是否有效
     */
    private boolean checkValidType(boolean isSkin, boolean isBluePrint, Type type) {
        if (!isBluePrint && "蓝图".equals(type.getCategoryName())) {
            return false;
        }
        return isSkin || !"涂装".equals(type.getCategoryName());
    }

    /**
     * 判断物品名称是否有效
     *
     * @param name 名称
     * @param type 物品
     * @return 是否有效
     */
    private boolean checkNameType(String name, Type type) {
        return type.getName().contains(name) || type.getNameEn().equalsIgnoreCase(name);
    }


    /**
     * 获取一个名字的真实名称
     *
     * @param name 名称
     * @param qq   查询人QQ，可以匹配用户自定义的别名
     * @return 真实名称
     */
    public String getRealName(String name, long qq) {

        // 优先查询用户自定义别名
        String userKey = CacheKey.EVE_TYPE_ALIAS + ":" + qq;
        if (redis.hExist(userKey, name)) {
            return redis.hGet(userKey, name);
        }
        if (redis.hExist(CacheKey.EVE_TYPE_ALIAS, name)) {
            return redis.hGet(CacheKey.EVE_TYPE_ALIAS, name);
        }
        return name;
    }

}
