package com.yuxuan66.support.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Sir丶雨轩
 * @since 2022/9/8
 */
@Data
public class BaseEntity<T extends Model<?>> extends Model<T>{

    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * 创建时间0
     */
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createId;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Timestamp updateTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateId;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
}
