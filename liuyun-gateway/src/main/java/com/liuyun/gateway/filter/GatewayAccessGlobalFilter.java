package com.liuyun.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/8 11:01
 **/
@Slf4j
@Order(0)
@Component
public class GatewayAccessGlobalFilter implements GlobalFilter {

    private static final String GATEWAY_TOKEN_HEADER = "Gateway_Authorization";

    private static final String GATEWAY_TOKEN = "liuyun:gateway";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().pathWithinApplication().value();
        HttpMethod method = request.getMethod();
        URI targetUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        request.mutate().header(GATEWAY_TOKEN_HEADER, new String(Base64Utils.encode(GATEWAY_TOKEN.getBytes())));
        return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpStatus statusCode = response.getStatusCode();
            log.info("请求路径 -> [{}],请求IP地址 -> [{}],请求方法 -> [{}],目标地址 -> [{}],响应码 -> [{}]",
                    path, remoteAddress, method, targetUri, statusCode);
        }));
    }
}
