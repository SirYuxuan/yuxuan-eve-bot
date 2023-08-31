package com.yuxuan66.bot.plugin.impl.eve.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * EVE所有的物品ID和名称(EveType)实体类
 *
 * 该实体类表示 EVE 中的物品信息，包括物品名称、描述等。
 *
 * @author makejava
 * @since 2021-12-15 14:49:32
 */
@Data
@TableName("eve_type")
public class Type extends Model<Type> implements Serializable {

    /**
     * 物品 ID
     */
    private Integer id;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品英文名称
     */
    private String nameEn;

    /**
     * 物品描述
     */
    private String description;

    /**
     * 物品英文描述
     */
    private String descriptionEn;

    /**
     * 物品所属分组的 ID
     */
    private Integer groupId;

    /**
     * 物品所属分组的名称
     */
    private String groupName;

    /**
     * 物品所属分组的英文名称
     */
    private String groupNameEn;

    /**
     * 物品的元组 ID
     */
    private Integer metaGroupId;

    /**
     * 物品的元组名称
     */
    private String metaGroupName;

    /**
     * 物品的元组英文名称
     */
    private String metaGroupNameEn;

    /**
     * 物品的市场分组 ID
     */
    private Integer marketGroupId;

    /**
     * 物品的市场分组名称
     */
    private String marketGroupName;

    /**
     * 物品的市场分组英文名称
     */
    private String marketGroupNameEn;

    /**
     * 物品的分类 ID
     */
    private Integer categoryId;

    /**
     * 物品的分类名称
     */
    private String categoryName;

    /**
     * 物品的分类英文名称
     */
    private String categoryNameEn;

    /**
     * 该字段不存在于数据库表中，用于存储名称的拼音
     */
    @TableField(exist = false)
    private String pinyin;
}
