package com.liuyun.api.config;

import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/27 19:39
 **/
@Slf4j
@Configuration
@EnableFeignClients({"com.liuyun.api.service.*"})
public class ApiFeignConfig {

    /**
     * 打印请求日志
     *
     * NONE: 不记录任何信息
     * BASIE:仅记录请求方法，URL以及响应状态码和执行时间
     * HEADERS:除了记录BASIE级别得信息之外，还会记录请求和响应得头信息
     * FULL：记录所有请求与响应得明细，包括头信息，请求体，元数据等
     *
     * @author wangdong
     * @date 2021/1/9 7:10 下午
     * @return feign.Logger.Level
     **/
    @Bean
    public feign.Logger.Level feignLogLevel() {
        return feign.Logger.Level.FULL;
    }

    /**
     * 配置请求重试
     *
     * @author wangdong
     * @date 2021/1/9 7:11 下午
     * @return feign.Retryer
     **/
    @Bean
    @Primary
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
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
