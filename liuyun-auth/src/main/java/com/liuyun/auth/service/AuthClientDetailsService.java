package com.liuyun.auth.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:56
 **/
public interface AuthClientDetailsService extends ClientDetailsService {

    /**
     * 加载 client 数据 放入缓存
     *
     * @author wangdong
     * @date 2020/12/15 8:34 下午
     **/
    void loadAllClientToCache();
}
