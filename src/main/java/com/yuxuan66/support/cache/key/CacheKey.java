package com.yuxuan66.support.cache.key;


import com.yuxuan66.support.cache.key.modules.EveCacheKey;
import com.yuxuan66.support.cache.key.modules.WebCacheKey;

/**
 * 缓存Key
 *
 * @author Sir丶雨轩
 * @since 2022/9/13
 */
public interface CacheKey extends EveCacheKey , WebCacheKey {


    /**
     * 系统核心配置Key
     */
    String CONFIG = "SYS_CONFIG";


}
