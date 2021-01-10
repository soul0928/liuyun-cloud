package com.liuyun.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/31 15:41
 **/
@Data
@RefreshScope
@ConfigurationProperties(prefix = "liuyun.security")
public class WebSecurityProperties {

    /**
     * 是否只能通过网关获取资源
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;

}
