package com.liuyun.api.service.user;

import com.liuyun.api.service.user.fallback.PermissionFeignServiceFallbackFactory;
import com.liuyun.utils.global.constants.ServiceNameConstants;
import com.liuyun.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/28 00:46
 **/
@FeignClient(value = ServiceNameConstants.LIUYUN_USER,
        path = "/sysPermission",
        fallbackFactory = PermissionFeignServiceFallbackFactory.class,
        contextId = "permissionFeignService",
        decode404 = true)
public interface PermissionFeignService {

    /**
     * 根据 用户ID 获取用户权限信息
     *
     * @param userId {@link Long} 用户ID
     * @return com.liuyun.core.result.Result<java.util.Set < java.lang.String>>
     * @author wangdong
     * @date 2020/12/25 1:20 下午
     **/
    @GetMapping("/getPermissionsByUserId/{userId}")
    Result<Set<String>> getPermissionsByUserId(@PathVariable("userId") Long userId);
}
