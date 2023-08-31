package com.yuxuan66.bot.utils;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Sir丶雨轩
 * @since 2023/8/24
 */
public class BotUtil {

    /**
     * 发送信息的辅助方法，根据事件类型发送到相应的目标。
     *
     * @param event   消息事件，用于判断事件类型和获取发送者/群组
     * @param message 信息的消息内容
     */
    public static void sendMessage(MessageEvent event, MessageChain message) {
        if (event instanceof GroupMessageEvent groupEvent) {
            Contact group = groupEvent.getGroup();
            group.sendMessage(message); // 发送到群组
        } else {
            event.getSender().sendMessage(message); // 发送给私聊发送者
        }
    }

    /**
     * 发送消息的辅助方法，根据事件类型发送消息。
     *
     * @param event   消息事件，用于判断事件类型和获取发送者/群组
     * @param message 要发送的消息内容
     */
    public static void sendMessage(MessageEvent event, String message) {
        sendMessage(event, MessageUtils.newChain(new PlainText(message)));
    }
}
