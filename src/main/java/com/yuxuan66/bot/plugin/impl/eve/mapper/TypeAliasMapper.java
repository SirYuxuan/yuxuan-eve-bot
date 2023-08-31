package com.yuxuan66.bot.plugin.impl.eve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxuan66.bot.plugin.impl.eve.entity.TypeAlias;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类型别名的数据库操作映射接口
 * 继承自MyBatis Plus的BaseMapper，提供了通用的数据库操作方法
 * 用于操作类型别名实体对应的数据库表
 * 注释说明：
 * - 该接口是一个数据库操作映射接口，用于操作类型别名实体对应的数据库表。
 * - 继承自MyBatis Plus的BaseMapper，拥有常用的数据库操作方法。
 * 注意：
 * - 由于该接口继承自BaseMapper<TypeAlias>，可以直接使用MyBatis Plus提供的方法，无需额外编写SQL。
 *
 * @author Sir丶雨轩
 * @since 2023/1/13
 */
@Mapper
public interface TypeAliasMapper extends BaseMapper<TypeAlias> {
}
