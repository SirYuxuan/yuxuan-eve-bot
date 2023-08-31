package com.yuxuan66.bot.plugin.definition;

import lombok.Getter;

/**
 * @author Sir丶雨轩
 * @since 2023/8/24
 */
@Getter
public enum MessageScope {

    GROUP("群聊",1),
    PRIVATE("私聊",2),
    ALL("全部",0),

    ;
    private final String name;
    private final int value;
    MessageScope(String name,int value){
        this.name = name;
        this.value = value;
    }

}
