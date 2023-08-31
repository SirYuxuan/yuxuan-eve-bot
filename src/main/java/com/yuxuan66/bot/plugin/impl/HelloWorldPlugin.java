package com.yuxuan66.bot.plugin.impl;

import com.yuxuan66.bot.ReConst;
import com.yuxuan66.bot.plugin.definition.MessagePlugin;
import com.yuxuan66.bot.plugin.definition.MessageScope;
import com.yuxuan66.bot.plugin.definition.PluginInfo;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

/**
 * HelloWorld插件
 * HelloWorldPlugin类实现了MessagePlugin接口，用于处理匹配到的消息。
 * 通过@Component注解标记为Spring组件，会被自动扫描和装配。
 *
 * @since 2023/8/24
 */
@Component
public class HelloWorldPlugin implements MessagePlugin {

    /**
     * 获取插件信息
     * 返回一个名称为"测试机器人反应插件"、优先级为1、适用范围为ALL的插件信息。
     *
     * @return 插件信息
     */
    @Override
    public PluginInfo info() {
        return new PluginInfo("测试机器人反应插件", 1, MessageScope.ALL);
    }

    /**
     * 匹配消息
     * 使用正则表达式匹配消息，不区分大小写。
     *
     * @return 匹配结果的Matcher对象
     */
    @Override
    public String match( ) {
        return ReConst.HELLO;
    }

    /**
     * 处理消息
     * 根据匹配结果和消息事件，发送消息回复。
     * 发送的消息内容为"world"。
     *
     * @param event   消息事件
     * @param matcher 匹配结果的Matcher对象
     * @param message 消息内容
     * @return 处理结果，这里始终返回false
     */
    @Override
    public boolean apply(MessageEvent event, Matcher matcher, String message) {
        sendMessage(event, "1");
        return false;
    }
}