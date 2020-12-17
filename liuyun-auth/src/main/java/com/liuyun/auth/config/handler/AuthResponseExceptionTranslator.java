package com.liuyun.auth.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 22:06
 **/
@Slf4j
@Component
public class AuthResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        log.error("123123123213123123123123123123");
        Throwable throwable = e.getCause();
        if (throwable instanceof InternalAuthenticationServiceException) {

        }
        return null;
    }

}
