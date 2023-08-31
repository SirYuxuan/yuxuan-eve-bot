package com.yuxuan66.bot.plugin.definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 插件基础信息
 * @author Sir丶雨轩
 * @since 2023/8/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PluginInfo {

    /**
     * 插件名称
     */
    private String name;

    /**
     * 执行排序
     */
    private int order;

    /**
     * 消息受理范围，默认为所有来源
     */
    private MessageScope scope = MessageScope.ALL;
}
