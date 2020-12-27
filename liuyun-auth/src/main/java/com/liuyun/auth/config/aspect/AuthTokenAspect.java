package com.liuyun.auth.config.aspect;

import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 04:32
 **/
@Slf4j
@Aspect
@Component
public class AuthTokenAspect {

    @SuppressWarnings("all")
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Result<OAuth2AccessToken> result = null;
        // 放行
        Object proceed = pjp.proceed();
        if (Objects.nonNull(proceed)) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                result = Result.success(body);
            } else {
                log.error("error:{}", responseEntity.getStatusCode().toString());
                result = Result.fail("登录失败!!!");
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(result);

    }


}
