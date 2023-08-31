package com.yuxuan66.bot.plugin.impl.cacx.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuxuan66.bot.plugin.impl.cacx.entity.consts.UserSex;
import com.yuxuan66.bot.plugin.impl.cacx.entity.consts.UserStatus;
import com.yuxuan66.support.base.BaseEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 系统-用户表(SysUser)实体类
 *
 * @author Sir丶雨轩
 * @since 2022-09-08 17:14:59
 */
@Data
@TableName("sys_user")
public class User extends BaseEntity<User> implements Serializable {

    @Serial
    private static final long serialVersionUID = -97197633279961034L;
    
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户QQ
     */
    private String qq;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 头像ID
     */
    private String avatar;

    /**
     * 用户状态，0=正常，1=冻结，2=锁定
     */
    private UserStatus status = UserStatus.NORMAL;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别 0=女,1=男,2=未知
     */
    private UserSex sex = UserSex.UNKNOWN;
    /**
     * 登录时间
     */
    private Timestamp loginTime;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 登录城市
     */
    private String loginCity;

    /**
     * 用户权限字符串
     */
    @TableField(exist = false)
    private Set<String> permissions;

    /**
     * 用户所属角色列表
     */
    @TableField(exist = false)
    private Set<Role> roles;

    /**
     * 用户包含的角色
     */
    @TableField(exist = false)
    private String roleNames;
    /**
     * 用户包含的角色ID
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 菜单列表
     */
    @TableField(exist = false)
    private List<Menu> menus = new ArrayList<>();

    /**
     * DiscordID 此项不为空代表已经绑定
     */
    private Long discordId;
    /**
     * Discord昵称
     */
    private String discordName;
    /**
     * Discord邮箱
     */
    private String discordEmail;
    /**
     * Discord认证Token
     */
    private String discordAccessToken;
    /**
     * Discord刷新Token
     */
    private String discordRefreshToken;
    /**
     * Discord认证Token过期时间
     */
    private Timestamp discordExpiresIn;



}

