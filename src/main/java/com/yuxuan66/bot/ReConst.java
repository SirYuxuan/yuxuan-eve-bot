package com.yuxuan66.bot;

/**
 * ReConst接口
 * ReConst接口定义了一些常量。
 *
 * @since 2023/8/24
 */
public interface ReConst {

    /**
     * 欢迎消息常量
     * HELLO常量表示"hello"字符串，用于匹配欢迎消息。
     */
    String HELLO = "hello";

    /**
     * 价格查询正则
     */
    String PRICE = "^(jita|\\.jita|ojita|\\.ojita|gjita|\\.gjita|col|\\.col|gcol|\\.gcol)(.*)$";
    /**
     * PAP查询
     */
    String PAP = "^(pap)$";

    /**
     * PAP查询
     */
    String ALIAS = "^(alias)\\s+(\\S+)\\s+(.*)$";
    /**
     * info查询
     */
    String INFO = "^(info)$";
    /**
     * rat查询
     */
    String RAT = "^(rat)$";
    /**
     * lp查询
     */
    String LP = "^(lp)$";
}