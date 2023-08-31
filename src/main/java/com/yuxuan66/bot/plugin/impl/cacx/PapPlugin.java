package com.yuxuan66.bot.plugin.impl.cacx;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuxuan66.bot.ReConst;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.impl.cacx.entity.AccountInfo;
import com.yuxuan66.bot.plugin.impl.cacx.entity.UserAccount;
import com.yuxuan66.bot.plugin.impl.cacx.service.AccountService;
import com.yuxuan66.support.cache.config.ConfigKit;
import com.yuxuan66.support.cache.key.CacheKey;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * PAP查询插件
 * 用于查询指定QQ的PAP信息。
 */
@Component
@RequiredArgsConstructor
public class PapPlugin implements MessagePlugin {

    private final AccountService accountService;

    /**
     * 指定插件匹配的关键词。
     * @return 匹配关键词
     */
    @Override
    public String match() {
        return ReConst.PAP;
    }

    /**
     * 应用插件逻辑。
     * @param event 消息事件
     * @param matcher 匹配器
     * @param message 消息内容
     * @return 应用结果
     */
    @Override
    public boolean apply(MessageEvent event, Matcher matcher, String message) {
        sendMessage(event, pap(event.getSender().getId()));
        return false;
    }

    /**
     * 查询指定QQ的PAP信息。
     *
     * @param qq QQ号码
     * @return PAP信息
     */
    public String pap(long qq) {
        AccountInfo accountInfo = accountService.getAccountByQQ(qq);
        Map<String, String> papMap = getPap();
        int thisMonth = DateUtil.thisMonth() + 1;
        StringBuilder result = new StringBuilder(accountInfo.getNickName() + " 您的" + thisMonth + "月份PAP统计如下:\r\n");
        double papTotal = 0;
        for (UserAccount userAccount : accountInfo.getAccountList()) {
            double pap = Convert.toDouble(papMap.get(Convert.toStr(userAccount.getCharacterId())), 0D);
            if (pap == 0) {
                continue;
            }
            papTotal += pap;
            result.append(userAccount.getCharacterName()).append("\t\t").append(pap).append(" PAP\r\n");
        }

        result.append("您在").append(thisMonth).append("月份的总PAP为").append(papTotal).append(",请继续努力");
        return result.toString();
    }

    /**
     * 获取PAP信息。
     *
     * @return PAP信息
     */
    private Map<String, String> getPap() {
        HttpRequest request = HttpUtil.createGet("https://seat.winterco.space/character/view/paps/2117284154?division=monthly&month=" + (DateUtil.thisMonth() + 1) + "&year=" + DateUtil.thisYear() + "&_=" + System.currentTimeMillis());
        request.cookie(ConfigKit.get(CacheKey.SEAT_COOKIE));
        request.header("referer", "https://seat.winterco.space/character/view/paps/2117284154");
        request.header("x-requested-with", "XMLHttpRequest");
        String body = request.execute().body();
        Map<String, String> map = new HashMap<>();
        if (JSONUtil.isTypeJSON(body)) {
            JSONArray array = JSON.parseObject(body).getJSONArray("data");
            for (Object o : array) {
                JSONObject data = (JSONObject) o;
                map.put(ReUtil.get("characters/(.*)/portrait", data.getString("character_id"), 1), data.getString("qty"));
            }
        }
        return map;
    }
}