package com.liuyun.api.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Feign请求拦截器
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/27 19:40
 **/
@Slf4j
public class ApiRequestInterceptor implements RequestInterceptor {

    private static final String FEIGN_TOKEN_HEADER = "Feign_Authorization";

    private static final String FEIGN_TOKEN = "liuyun:feign";

    /**
     * la拦截所有请求, 添加token数据
     * @author wangdong
     * @date 2020/7/27 7:42 下午
     * @param requestTemplate 请求构建器
     **/
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (Objects.nonNull(request)) {
            requestTemplate.header(FEIGN_TOKEN_HEADER, new String(Base64Utils.encode(FEIGN_TOKEN.getBytes())));
        }
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
