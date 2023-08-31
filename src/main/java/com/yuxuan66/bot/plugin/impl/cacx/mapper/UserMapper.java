package com.yuxuan66.bot.plugin.impl.cacx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxuan66.bot.plugin.impl.cacx.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户操作
 * @author Sir丶雨轩
 * @since 2022/9/8
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户id查询用户详细信息，包含角色、菜单、部门、头像等
     *
     * @param userId 用户id
     * @return 用户详情
     */
    User findUserById(Long userId);

    /**
     * 查询拥有指定权限的用户
     * @param permCode 权限编码
     * @return 用户列表
     */
    List<User> selectByPermCode(String permCode);
}
