package com.yuxuan66.bot.plugin.impl.cacx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuxuan66.bot.plugin.impl.cacx.entity.consts.MenuType;
import com.yuxuan66.support.base.BaseEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统-菜单表(SysMenu)实体类
 *
 * @author Sir丶雨轩
 * @since 2022-09-16 10:11:13
 */
@Data
@TableName("sys_menu")
public class Menu extends BaseEntity<Menu> implements Serializable {

    @Serial
    private static final long serialVersionUID = 413567790353332932L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID
     */
    private Long pid = 0L;
    /**
     * 菜单类型 0=目录,1=菜单,2=按钮
     */
    private MenuType type;
    /**
     * 菜单图标
     */
    private String icon;

    private String name;

    @TableField(exist = false)
    private String title;


    /**
     * 是否外链
     */
    private Boolean isLink;

    /**
     * 是否内嵌
     */
    private Boolean frame;
    /**
     * 是否缓存
     */
    private Boolean cache;
    /**
     * 外链地址
     */
    private String linkUrl;


    /**
     * 是否可见
     */
    private Boolean hidden;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 排序号
     */
    private Integer sort;


}

