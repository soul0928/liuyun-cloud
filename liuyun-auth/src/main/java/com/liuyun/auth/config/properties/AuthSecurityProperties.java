package com.liuyun.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/31 15:41
 **/
@Data
@RefreshScope
@ConfigurationProperties(prefix = "liuyun.security")
public class AuthSecurityProperties {

    /**
     * 是否只能通过网关获取资源
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;

    /**
     * 免认证资源路径
     */
    private AuthPermitProperties ignore = new AuthPermitProperties();

    @Data
    public static class AuthPermitProperties {

        /**
         * 监控中心和swagger需要访问的url
         */
        private static final String[] ENDPOINTS = {
                "/oauth/**",
                "/actuator/**",
                "/*/v2/api-docs",
                "/swagger/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/druid/**"
        };

        /**
         * 设置不用认证的url
         */
        private String[] authenticationUrls = {};

        /**
         * 设置认证后不需要判断具体权限的url，所有登录的人都能访问
         */
        private String[] authorizationUrls = {};

        public String[] GetAllUrls() {
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(ENDPOINTS));
            list.addAll(Arrays.asList(authenticationUrls));
            list.addAll(Arrays.asList(authorizationUrls));
            return list.toArray(new String[0]);
        }
    }

}
