package com.yuxuan66.bot.plugin.impl.cacx;

import com.yuxuan66.bot.ReConst;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.impl.cacx.entity.AccountInfo;
import com.yuxuan66.bot.plugin.impl.cacx.entity.UserAccount;
import com.yuxuan66.bot.plugin.impl.cacx.service.AccountService;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;

/**
 * LP插件，用于查询指定QQ的LP信息。
 * LP（Loyalty Points）是一种积分制度，用于统计用户在混沌中的活跃度。
 */
@Component
@RequiredArgsConstructor
public class LpPlugin implements MessagePlugin {
    private final AccountService accountService;

    /**
     * 指定插件匹配的关键词。
     * @return 匹配关键词
     */
    @Override
    public String match() {
        return ReConst.LP;
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
        sendMessage(event, lp(event.getSender().getId()));
        return false;
    }

    /**
     * 查询指定QQ的LP信息。
     *
     * @param qq QQ号码
     * @return LP信息
     */
    public String lp(long qq) {
        AccountInfo accountInfo = accountService.getAccountByQQ(qq);
        String result = accountInfo.getNickName() + " 您的LP统计如下:\r\n";
        result += "共获得: " + accountInfo.getAccountList().stream().map(UserAccount::getLpTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        result += "\r\n已使用: " + accountInfo.getAccountList().stream().map(UserAccount::getLpUse).reduce(BigDecimal.ZERO, BigDecimal::add);
        result += "\r\n现剩余: " + accountInfo.getAccountList().stream().map(UserAccount::getLpNow).reduce(BigDecimal.ZERO, BigDecimal::add);
        result += "\r\n感谢您为混沌做出的贡献!";
        return result;
    }
}