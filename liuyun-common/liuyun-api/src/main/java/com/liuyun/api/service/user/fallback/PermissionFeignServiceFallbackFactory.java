package com.liuyun.api.service.user.fallback;

import com.liuyun.api.service.user.PermissionFeignService;
import com.liuyun.utils.result.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/28 00:47
 **/
@Slf4j
@Component
public class PermissionFeignServiceFallbackFactory implements FallbackFactory<PermissionFeignService> {

    @Override
    public PermissionFeignService create(Throwable throwable) {
        return new PermissionFeignService() {
            @Override
            public Result<Set<String>> getPermissionsByUserId(Long userId) {
                log.error("根据 用户ID 获取用户权限信息异常 -> [{}]", userId, throwable);
                return Result.fail();
            }
        };
    }
}
