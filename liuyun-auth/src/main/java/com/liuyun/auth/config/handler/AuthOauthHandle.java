package com.liuyun.auth.config.handler;

import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.core.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * 认证处理器
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/26 20:04
 **/
public class AuthOauthHandle {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthClientDetailsService authClientDetailsService;


    private ClientDetails getClient(String clientId, String clientSecret) {
        ClientDetails clientDetails = authClientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new AuthException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        return clientDetails;
    }
}
