package com.yuxuan66.bot.listener;

import com.yuxuan66.bot.CoreBot;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.definition.MessageScope;
import com.yuxuan66.bot.utils.BotUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EveBot消息事件监听器
 * EveBotMessageEventListener类实现了Consumer接口，用于处理消息事件。
 * 通过@Component注解标记为Spring组件，会被自动扫描和装配。
 *
 * @since 2023/8/24
 */
@Component
public class EveBotMessageEventListener implements Consumer<MessageEvent> {

    /**
     * 处理消息事件
     * accept方法是Consumer接口中的抽象方法，用于处理消息事件。
     * 遍历已注册的消息插件，判断消息是否匹配插件的处理规则。
     * 如果匹配，则调用插件的apply方法处理消息。
     * 如果处理过程中发生异常，则根据消息事件的类型发送错误信息。
     *
     * @param event 消息事件
     */
    @Override
    public void accept(MessageEvent event) {

        // 判断事件是否为群聊消息事件
        boolean isGroup = event instanceof GroupMessageEvent;

        // 遍历已注册的消息插件列表
        for (MessagePlugin plugin : CoreBot.messagePluginList) {

            // 插件与事件的适用范围不匹配，跳过该插件
            if (!isPluginScopeMatch(plugin, isGroup)) {
                continue;
            }

            // 调用封装的处理插件逻辑方法
            if (!handlePluginLogic(event, plugin)) {
                break; // 插件处理成功，终止后续插件的执行
            }

        }
    }

    /**
     * 检查插件与事件的适用范围是否匹配的辅助方法。
     *
     * @param plugin 插件对象，用于获取适用范围
     * @param isGroup 是否为群聊事件
     * @return 插件是否适用于事件的范围
     */
    private boolean isPluginScopeMatch(MessagePlugin plugin, boolean isGroup) {
        if (plugin.info().getScope() == MessageScope.GROUP && !isGroup) {
            return false; // 插件要求群聊范围，但事件不是群聊事件
        }
        // 插件要求私聊范围，但事件是群聊事件
        return plugin.info().getScope() != MessageScope.PRIVATE || !isGroup;
    }

    /**
     * 处理插件逻辑的辅助方法。
     *
     * @param event 消息事件
     * @param plugin 插件对象
     * @return 是否继续处理下一个插件
     */
    private boolean handlePluginLogic(MessageEvent event, MessagePlugin plugin) {
        String messageContent = event.getMessage().contentToString();

        Matcher matcher = Pattern.compile(plugin.match(),Pattern.CASE_INSENSITIVE).matcher(messageContent);

        if (matcher.find()) {
            try {
                if (!plugin.apply(event, matcher, messageContent)) {
                    return false; // 插件处理成功，不需要继续处理下一个插件
                }
            } catch (Exception e) {
                BotUtil.sendMessage(event, e.getMessage()); // 处理插件应用过程中的异常
                return false; // 插件处理失败，不需要继续处理下一个插件
            }
        }

        return true; // 插件处理成功或者未匹配，可以继续处理下一个插件
    }
}