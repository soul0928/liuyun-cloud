package com.liuyun.auth.config;

import cn.hutool.core.util.StrUtil;
import com.liuyun.core.result.ResponseUtil;
import com.liuyun.core.result.Result;
import com.liuyun.oauth2.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证错误处理
 *
 * @author mall
 */
@Slf4j
@Configuration
public class AuthSecurityHandlerConfig {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 退出登录
     *
     * @return org.springframework.security.web.authentication.logout.LogoutHandler
     * @author wangdong
     * @date 2020/12/25 5:29 下午
     **/
    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            Assert.notNull(tokenStore, "tokenStore must be set");
            String token = AuthUtil.extractToken(request);
            if (StrUtil.isNotBlank(token)) {
                OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
                OAuth2RefreshToken refreshToken;
                if (existingAccessToken != null) {
                    if (existingAccessToken.getRefreshToken() != null) {
                        log.info("remove refreshToken -> [{}]", existingAccessToken.getRefreshToken());
                        refreshToken = existingAccessToken.getRefreshToken();
                        tokenStore.removeRefreshToken(refreshToken);
                    }
                    log.info("remove existingAccessToken -> [{}]", existingAccessToken);
                    tokenStore.removeAccessToken(existingAccessToken);
                }
            }
        };
    }

    /**
     * 退出登录成功返回信息
     *
     * @return org.springframework.security.web.authentication.logout.LogoutSuccessHandler
     * @author wangdong
     * @date 2020/12/25 4:54 下午
     **/
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> ResponseUtil.out(response, Result.success("退出登录成功!!!"));
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                ResponseUtil.out(response, Result.success(authentication));
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
                ResponseUtil.out(response, Result.fail(e.getMessage()));
            }
        };
    }

}
