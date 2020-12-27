package com.liuyun.oauth2.interceptor;

import cn.hutool.core.util.StrUtil;
import com.liuyun.core.result.ResponseUtil;
import com.liuyun.core.result.Result;
import com.liuyun.oauth2.constants.AuthConstants;
import com.liuyun.oauth2.properties.AuthSecurityProperties;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 配置是否只能通过网关请求
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/26 23:46
 **/
public class AuthServerProtectInterceptor implements HandlerInterceptor {

    private final AuthSecurityProperties authSecurityProperties;

    public AuthServerProtectInterceptor(AuthSecurityProperties authSecurityProperties) {
        this.authSecurityProperties = authSecurityProperties;
    }

    /**
     * 配置是否只能通过网关请求
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler  {@link Object}
     * @return boolean
     * @author wangdong
     * @date 2020/12/26 11:50 下午
     **/
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (!authSecurityProperties.getOnlyFetchByGateway()) {
            return true;
        }
        String token = request.getHeader(AuthConstants.GATEWAY_TOKEN_HEADER);
        String gatewayToken = new String(Base64Utils.encode(AuthConstants.GATEWAY_TOKEN_VALUE.getBytes()));
        if (StrUtil.equals(gatewayToken, token)) {
            return true;
        }
        ResponseUtil.out(response, Result.fail("请通过网关获取资源!!!"));
        return false;
    }


}
