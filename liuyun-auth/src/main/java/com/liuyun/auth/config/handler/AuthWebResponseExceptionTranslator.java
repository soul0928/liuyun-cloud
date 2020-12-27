package com.liuyun.auth.config.handler;

import com.liuyun.auth.config.exception.AuthOauth2Exception;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 02:39
 **/
@Slf4j
@Component
@SuppressWarnings("all")
public class AuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<?> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception = null;
        if (e instanceof InvalidClientException) {
            oAuth2Exception = new AuthOauth2Exception(e.getMessage(), e);
        }
        if (e instanceof InternalAuthenticationServiceException) {
            oAuth2Exception = new AuthOauth2Exception(e.getMessage(), e);
        }
        if (e instanceof UnsupportedGrantTypeException) {
            oAuth2Exception = new AuthOauth2Exception("不支持该认证类型!!!", e);
        }
        if (e instanceof InvalidTokenException
                && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
            oAuth2Exception = new AuthOauth2Exception("刷新令牌已过期，请重新登录!!!", e);
        }
        if (e instanceof InvalidScopeException) {
            oAuth2Exception = new AuthOauth2Exception("不是有效的scope值!!!", e);
        }
        if (e instanceof RedirectMismatchException) {
            oAuth2Exception = new AuthOauth2Exception("redirect_uri值不正确!!!", e);
        }
        if (e instanceof BadClientCredentialsException) {
            oAuth2Exception = new AuthOauth2Exception("client值不合法!!!", e);
        }
        if (e instanceof UnsupportedResponseTypeException) {
            oAuth2Exception = new AuthOauth2Exception("不是合法的response_type值!!!", e);
        }
        if (e instanceof InvalidGrantException) {
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                oAuth2Exception = new AuthOauth2Exception("refresh token无效!!!", e);
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid authorization code")) {
                String code = StringUtils.substringAfterLast(e.getMessage(), ": ");
                oAuth2Exception = new AuthOauth2Exception("授权码" + code + "不合法!!!", e);
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
                oAuth2Exception = new AuthOauth2Exception("用户已被锁定，请联系管理员!!!", e);
            }
            oAuth2Exception = new AuthOauth2Exception("用户名或密码错误!!!", e);
        }
        if (Objects.isNull(oAuth2Exception)) {
            oAuth2Exception = new AuthOauth2Exception("系统异常!!!", e);
        }
        AuthOauth2Exception exception = new AuthOauth2Exception(oAuth2Exception.getMessage(), oAuth2Exception);
        return new ResponseEntity<>(exception, HttpStatus.valueOf(oAuth2Exception.getHttpErrorCode()));
    }
}
