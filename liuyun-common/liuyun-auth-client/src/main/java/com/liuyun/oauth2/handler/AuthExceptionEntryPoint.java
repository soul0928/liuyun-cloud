package com.liuyun.oauth2.handler;

import com.liuyun.core.result.ResponseUtil;
import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未登录
 */
@Slf4j
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String requestUri = request.getRequestURI();
        String message = "访问令牌不合法!!!";
        log.error("客户端访问 -> [{}], 请求失败 -> [{}]", requestUri, message, authException);
        ResponseUtil.out(response, Result.fail(HttpStatus.UNAUTHORIZED.value(), message));
    }
}
