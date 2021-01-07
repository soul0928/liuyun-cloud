package com.liuyun.gateway.service;

import com.liuyun.gateway.config.feign.FeignConfig;
import com.liuyun.gateway.service.fallback.SysRolePermissionFeignServiceFallbackFactory;
import com.liuyun.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/3 17:37
 **/
@FeignClient(value = "liuyun-user",
        path = "/sysRolePermission",
        fallbackFactory = SysRolePermissionFeignServiceFallbackFactory.class,
        contextId = "sysRolePermissionFeignService",
        configuration = FeignConfig.class)
public interface SysRolePermissionFeignService {

    /**
     * 获取路径需要角色信息
     *
     * @param map {@link Map} 请求路径
     * @return com.liuyun.utils.result.Result<java.lang.Object>
     * @author wangdong
     * @date 2021/1/3 4:56 下午
     **/
    @PostMapping(value = "/getRoleByUrl")
    Result<Set<String>> getRoleByUrl(@RequestBody Map<String, String> map);
}
