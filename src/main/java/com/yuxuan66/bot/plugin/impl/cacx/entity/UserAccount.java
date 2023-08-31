package com.yuxuan66.bot.plugin.impl.cacx.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuxuan66.support.base.BaseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 军团用户角色表(CorpUserAccount)实体类
 *
 * @author Sir丶雨轩
 * @since 2022-12-12 16:08:00
 */
@Slf4j
@Data
@ToString
@TableName("corp_user_account")
public class UserAccount extends BaseEntity<UserAccount> implements Serializable {
    @Serial
    private static final long serialVersionUID = -80069397898096666L;

    /**
     * 角色名称
     */
    private String characterName;
    /**
     * 角色ID
     */
    private Integer characterId;
    /**
     * 系统用户ID
     */
    private Long userId;
    /**
     * 主角色
     */
    private Boolean isMain;
    /**
     * ESI/Token
     */
    private String accessToken;
    /**
     * AccessToken过期时间
     */
    private Timestamp accessExp;
    /**
     * ESI/Token
     */
    private String refreshToken;
    /**
     * 军团ID
     */
    private Integer corpId;
    /**
     * 军团名称
     */
    private String corpName;
    /**
     * 联盟ID
     */
    private Integer allianceId;
    /**
     * 联盟名称
     */
    private String allianceName;
    /**
     * ISK数量
     */
    private Double isk;
    /**
     * 技能点数量
     */
    private Long skill;
    /**
     * LP当前数量
     */
    private BigDecimal lpNow;
    /**
     * LP总计获得数量
     */
    private BigDecimal lpTotal;
    /**
     * LP已使用数量
     */
    private BigDecimal lpUse;
    /**
     * 入团时间(加入主军团的时间)
     */
    private Timestamp joinTime;

    /**
     * 未分配技能点
     */
    private Integer unallocatedSp;

    @TableField(exist = false)
    private User user;


}

