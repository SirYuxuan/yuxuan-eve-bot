package com.yuxuan66.bot.plugin.impl.cacx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Sir丶雨轩
 * @since 2023/8/25
 */
@Data
@AllArgsConstructor
public  class AccountInfo {
    private String nickName;
    private Long userId;
    private List<UserAccount> accountList;
}