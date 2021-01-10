package com.liuyun.web.interceptor;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.lang.StringUtils;
import com.liuyun.utils.result.ResponseUtil;
import com.liuyun.utils.result.Result;
import com.liuyun.utils.servlet.ServletUtil;
import com.liuyun.web.properties.WebSecurityProperties;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置是否只能通过网关请求
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/26 23:46
 **/
public class WebServerProtectInterceptor implements HandlerInterceptor {

    private static final String GATEWAY_TOKEN_HEADER = "Gateway_Authorization";
    private static final String FEIGN_TOKEN_HEADER = "Feign_Authorization";

    private static final String GATEWAY_TOKEN = "liuyun:gateway";
    private static final String FEIGN_TOKEN = "liuyun:feign";

    private final WebSecurityProperties webSecurityProperties;

    public WebServerProtectInterceptor(WebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
    }


    /**
     * 配置是否只能通过网关请求
     *
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @author wangdong
     * @date 2021/1/10 2:30 下午
     * @return boolean
     **/
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (!webSecurityProperties.getOnlyFetchByGateway()) {
            return true;
        }
        String gatewayToken = ServletUtil.getHeader(request, GATEWAY_TOKEN_HEADER);
        String feignToken = ServletUtil.getHeader(request, FEIGN_TOKEN_HEADER);
        if (StringUtils.equals(new String(Base64Utils.encode(GATEWAY_TOKEN.getBytes())), gatewayToken)
                || StringUtils.equals(new String(Base64Utils.encode(FEIGN_TOKEN.getBytes())), feignToken)) {
            return true;
        }
        ResponseUtil.out(response, Result.fail(GlobalResultEnum.USER_ERROR.getCode(), "请通过网关获取资源!!!"));
        return false;
    }

    public static void main(String[] args) {
        String gatewayToken = new String(Base64Utils.encode("liuyun:gateway".getBytes()));
        System.out.println(gatewayToken);
        String feignToken = new String(Base64Utils.encode("liuyun:feign".getBytes()));
        System.out.println(feignToken);
    }

}
