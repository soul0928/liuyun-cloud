package com.liuyun.oauth2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:23
 **/
@Data
@RefreshScope
@ConfigurationProperties(prefix = "liuyun.security")
public class AuthSecurityProperties {

    private AuthPermitProperties ignore = new AuthPermitProperties();

}
