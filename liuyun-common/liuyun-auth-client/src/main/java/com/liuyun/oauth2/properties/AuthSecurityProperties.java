package com.liuyun.oauth2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:23
 **/
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "liuyun.security")
public class AuthSecurityProperties {

    /**
     * 是否开启安全配置
     */
    private Boolean enable = Boolean.TRUE;

    /**
     * 免认证资源路径，支持通配符
     */
    private AuthPermitProperties ignore = new AuthPermitProperties();

    /**
     * 是否只能通过网关获取资源
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;
}
