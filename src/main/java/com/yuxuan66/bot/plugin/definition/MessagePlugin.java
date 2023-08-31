package com.yuxuan66.bot.plugin.definition;

import com.yuxuan66.bot.utils.BotUtil;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.regex.Matcher;

/**
 * 消息插件接口
 * MessagePlugin接口定义了消息插件的基本行为。
 * 默认提供info方法，返回一个默认的插件信息。
 * match方法用于匹配消息是否符合插件的处理规则。
 * apply方法用于应用插件的处理逻辑。
 *
 * @since 2023/8/24
 */
public interface MessagePlugin {

    /**
     * 获取插件信息
     * 默认返回一个未知插件名、优先级为999、适用范围为ALL的插件信息。
     *
     * @return 插件信息
     */
    default PluginInfo info() {
        return new PluginInfo("未知", 999, MessageScope.ALL);
    }


    /**
     * 返回一段正则用来匹配当前插件是否可以执行
     * @return 正则内容
     */
    String match();

    /**
     * 应用插件处理
     * 根据匹配结果和消息事件，应用插件的处理逻辑。
     *
     * @param event   消息事件
     * @param matcher 匹配结果的Matcher对象
     * @param message 消息内容
     * @return 处理结果，true表示成功处理，false表示处理失败
     */
    boolean apply(MessageEvent event, Matcher matcher, String message);

    /**
     * 使用 MessageChain 发送消息的默认方法。
     *
     * @param event   消息事件
     * @param message 要发送的消息，以 MessageChain 形式表示
     */
    default void sendMessage(MessageEvent event, MessageChain message) {
        BotUtil.sendMessage(event, message);
    }

    /**
     * 使用纯文本字符串发送消息的默认方法。
     *
     * @param event   消息事件
     * @param message 要发送的纯文本消息
     */
    default void sendMessage(MessageEvent event, String message) {
        BotUtil.sendMessage(event, message);
    }

}