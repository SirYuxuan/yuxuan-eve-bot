package com.yuxuan66.bot.plugin.impl.cacx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxuan66.bot.plugin.impl.cacx.entity.AccountInfo;
import com.yuxuan66.bot.plugin.impl.cacx.entity.User;
import com.yuxuan66.bot.plugin.impl.cacx.entity.UserAccount;
import com.yuxuan66.bot.plugin.impl.cacx.mapper.UserAccountMapper;
import com.yuxuan66.bot.plugin.impl.cacx.mapper.UserMapper;
import com.yuxuan66.support.cache.config.ConfigKit;
import com.yuxuan66.support.cache.key.CacheKey;
import com.yuxuan66.support.exception.BizException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * AccountService类，提供账号相关操作的服务
 * 通过Spring的@Service注解标记为一个服务类
 *
 * @since 2023/8/25
 */
@Service
public class AccountService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserAccountMapper userAccountMapper;

    /**
     * 根据QQ获取账号信息
     *
     * @param qq 用户QQ号
     * @return 账号信息
     * @throws BizException 当业务出现异常时抛出
     */
    public AccountInfo getAccountByQQ(long qq) throws BizException {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("qq", qq));
        if (userList.isEmpty()) {
            throw new BizException(withSite("没有找到账号信息，请先注册军团网站并绑定QQ。"));
        }
        List<UserAccount> accountList = userAccountMapper.selectList(new QueryWrapper<UserAccount>().in("user_id", userList.stream().map(User::getId).toList()));
        if (accountList.isEmpty()) {
            throw new BizException(withSite("您已注册军团系统，但没有绑定角色。"));
        }
        return new AccountInfo(userList.get(0).getNickName(), userList.get(0).getId(), accountList);
    }

    /**
     * 在消息后添加主页链接并返回结果字符串
     *
     * @param message 原始消息
     * @return 添加主页链接后的结果字符串
     */
    private String withSite(String message) {
        return message + " \r" + ConfigKit.get(CacheKey.WEB_SITE);
    }
}
