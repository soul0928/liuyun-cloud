package com.liuyun.api.config;

import cn.hutool.extra.servlet.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * Feign请求拦截器
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/27 19:40
 **/
@Slf4j
public class ApiRequestInterceptor implements RequestInterceptor {

    @Bean
    @Primary
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    /**
     * la拦截所有请求, 添加token数据
     * @author wangdong
     * @date 2020/7/27 7:42 下午
     * @param requestTemplate 请求构建器
     **/
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String authorization = null;
        HttpServletRequest request = getHttpServletRequest();

        if (request != null) {
            authorization = ServletUtil.getHeader(request, "Authorization", StandardCharsets.UTF_8);
            requestTemplate.header("Authorization", authorization);
        }
        log.info("Feign 请求拦截器获得 Authorization -> [{}];", authorization);
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                return ((ServletRequestAttributes) requestAttributes).getRequest();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
