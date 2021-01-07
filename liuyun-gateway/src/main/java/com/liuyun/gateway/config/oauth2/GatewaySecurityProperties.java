package com.liuyun.gateway.config.oauth2;

import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/3 13:42
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "liuyun.security")
public class GatewaySecurityProperties {

    private GatewayIgnoreProperties ignore = new GatewayIgnoreProperties();

    @Setter
    public static class GatewayIgnoreProperties {

        private static List<String> endpoint = Arrays.asList(
                "/auth/oauth/**",
                "/doc.html",
                "/favicon.ico",
                "/webjars/**",
                "/swagger-resources/**",
                "/*/v2/api-docs"
        );


        /**
         * 无需登录的 url
         */
        private List<String> authenticateUrls = new ArrayList<>();

        /**
         * 无需鉴权的 url
         */
        private List<String> authenticationUrls = new ArrayList<>();

        public List<String> getAuthenticateUrls() {
            List<String> urls = new ArrayList<>();
            urls.addAll(this.authenticateUrls);
            urls.addAll(endpoint);
            return urls;
        }

        public List<String> getAuthenticationUrls() {
            List<String> urls = new ArrayList<>();
            urls.addAll(this.authenticationUrls);
            urls.addAll(endpoint);
            return urls;
        }
    }
}
