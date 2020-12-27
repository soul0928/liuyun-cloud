package com.liuyun.oauth2.handler;

import com.liuyun.core.result.ResponseUtil;
import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MrBird
 */
@Slf4j
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        String requestUri = request.getRequestURI();
        String message = "没有权限访问该资源!!!";
        log.error("客户端访问 -> [{}], 请求失败 -> [{}]", requestUri, message, accessDeniedException);
        ResponseUtil.out(response, Result.fail(HttpStatus.FORBIDDEN.value(), message));
    }
}
