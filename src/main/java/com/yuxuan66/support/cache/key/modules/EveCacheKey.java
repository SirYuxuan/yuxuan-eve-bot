package com.yuxuan66.support.cache.key.modules;

/**
 * @author Sir丶雨轩
 * @since 2022/12/13
 */
public interface EveCacheKey {


    //************************ EVE ESI配置key ***************************/

    /**
     * EVE ESI ClientID
     */
    String EVE_ESI_CLIENT_ID = "eve.esi.client";
    /**
     * EVE ESI secretKey
     */
    String EVE_ESI_SECRET_KEY = "eve.esi.secret";
    /**
     * 物品物品别名
     */
    String EVE_TYPE_ALIAS = "eve:type:alias";


    /**
     * 联盟通讯Cookie
     */
    String SEAT_COOKIE = "seat.cookie";
}
