package com.yuxuan66.bot.plugin.impl.eve.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 类型别名实体类
 * 用于映射数据库中的eve_type_alias表
 * 注释说明：
 * - 该类表示EVE游戏中的类型别名实体，对应数据库表eve_type_alias。
 * - 使用Lombok的@Data注解，自动生成属性的getter、setter等方法。
 * - 使用@TableName注解指定映射的数据库表名。
 * - 继承自MyBatis Plus的Model类，提供了一些便于操作数据库的方法。
 * - 类中的字段分别表示别名的ID、名称、实际别名的值、QQ号。
 * 注意：
 * - 使用@TableField("`alias`")注解映射数据库中的alias字段，因为alias是SQL关键字。
 *
 * @author Sir丶雨轩
 * @since 2023/1/13
 */
@Data
@TableName("eve_type_alias")
public class TypeAlias extends Model<TypeAlias> {

    /**
     * 别名ID
     */
    private Long id;

    /**
     * 别名名称
     */
    private String name;

    /**
     * 实际别名的值，注意使用@TableField("`alias`")进行映射
     */
    @TableField("`alias`")
    private String alias;

    /**
     * QQ号
     */
    private Long qq;
}
