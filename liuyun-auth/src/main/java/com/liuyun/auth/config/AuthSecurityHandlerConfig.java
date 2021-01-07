package com.liuyun.auth.config;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.result.ResponseUtil;
import com.liuyun.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Security 认证后续处理
 *
 * @author wangdong
 * @date 2021/1/2 4:39 下午
 **/
@Slf4j
@Configuration
public class AuthSecurityHandlerConfig {

    /**
     * 认证错误处理
     *
     * @return org.springframework.security.web.authentication.AuthenticationFailureHandler
     * @author wangdong
     * @date 2021/1/2 4:39 下午
     **/
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, e) -> ResponseUtil.out(response, Result.fail(GlobalResultEnum.USERNAME_OR_PASSWORD_ERROR));
    }

}
