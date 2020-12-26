package com.liuyun.auth.config;

import cn.hutool.core.util.StrUtil;
import com.liuyun.auth.config.exception.AuthOauth2Exception;
import com.liuyun.core.result.ResponseUtil;
import com.liuyun.core.result.Result;
import com.liuyun.oauth2.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

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
     * 登陆失败，返回401
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        System.out.println("loginFailureHandler");
        return null;
    }

    /**
     * 自定义异常转换类
     *
     * @return org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator<org.springframework.security.oauth2.common.exceptions.OAuth2Exception>
     * @author wangdong
     * @date 2020/12/25 9:54 下午
     **/
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                OAuth2Exception oAuth2Exception;
                if (e instanceof InvalidClientException) {
                    oAuth2Exception = new AuthOauth2Exception(e.getMessage(), e);
                } else if (e instanceof InvalidGrantException) {
                    oAuth2Exception = new AuthOauth2Exception(e.getMessage(), e);
                } else {
                    oAuth2Exception = new UnsupportedResponseTypeException("系统异常!!!", e);
                }
                AuthOauth2Exception exception = new AuthOauth2Exception(oAuth2Exception.getMessage(), oAuth2Exception);
                return new ResponseEntity<>(exception, HttpStatus.valueOf(oAuth2Exception.getHttpErrorCode()));
            }
        };
    }

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
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            Result<Object> result;
            if (e instanceof InsufficientAuthenticationException) {
                result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "请先登录!!!");
            } else {
                result = Result.fail();
            }

            ResponseUtil.out(response, result);
        };
    }
}
