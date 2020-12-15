package com.liuyun.api.config;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/27 19:39
 **/
@Slf4j
@Configuration
@EnableFeignClients({"com.liuyun.api.service.*"})
public class ApiConfig {

    /**
     * 日志级别
     *
     * @return feign.Logger.Level
     * @author wangdong
     * @date 2020/12/14 4:09 下午
     **/
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的token,各个微服务将token设置到环境变量中来达到通用
     *
     * @return FeignBasicAuthRequestInterceptor
     * @author wangdong
     * @date 2020/12/14 4:09 下午
     **/
    @Bean
    public ApiRequestInterceptor apiRequestInterceptor() {
        return new ApiRequestInterceptor();
    }

}
