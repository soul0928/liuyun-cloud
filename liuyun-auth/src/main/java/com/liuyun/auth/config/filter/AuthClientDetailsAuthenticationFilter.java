package com.liuyun.auth.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/26 17:54
 **/
@Slf4j
// @Component
public class AuthClientDetailsAuthenticationFilter extends OncePerRequestFilter {

    private static final String INTERCEPT_URL = "/oauth/token";

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 只有获取token的时候需要携带携带客户端信息，放过其他
        if (StringUtils.equals(INTERCEPT_URL, request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String client_id = request.getParameter("client_id");
        String client_secret = request.getParameter("client_secret");
        System.out.println(client_id);
        System.out.println(client_secret);

    }
}
