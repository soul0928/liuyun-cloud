package com.liuyun.auth.service.impl;

import com.liuyun.auth.service.AuthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 14:30
 **/
@Slf4j
@Component
public class AuthClientDetailsServiceImpl implements AuthClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return null;
    }

    @Override
    public void loadAllClientToCache() {

    }
}
