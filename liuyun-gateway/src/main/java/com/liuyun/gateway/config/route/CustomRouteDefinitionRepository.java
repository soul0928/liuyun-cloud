package com.liuyun.gateway.config.route;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 自定义 动态路由
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/11 15:18
 **/
@Slf4j
//@Component
public class CustomRouteDefinitionRepository /*implements RouteDefinitionRepository */{

    /**
     * 启动的时候加载
     *
     * @return reactor.core.publisher.Flux<org.springframework.cloud.gateway.route.RouteDefinition>
     * @author wangdong
     * @date 2020/12/11 3:21 下午
     **/
    //@Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return null;
    }

   // @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    //@Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    public static void main(String[] args) {
        // PredicateDefinition 设置断言
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("");
        predicateDefinition.setArgs(Maps.newHashMap());
        // 设置过滤器
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName("");
        filterDefinition.setArgs(Maps.newHashMap());
    }
}
