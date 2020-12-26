package com.liuyun.api.service.user.fallback;

import com.liuyun.api.service.user.RoleFeignService;
import com.liuyun.core.result.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/25 13:39
 **/
@Slf4j
@Component
public class RoleFeignServiceFallbackFactory implements FallbackFactory<RoleFeignService> {
    @Override
    public RoleFeignService create(Throwable throwable) {
        return userId -> {
            log.error("根据 用户ID 获取用户角色信息异常 -> [{}]", userId, throwable);
            return Result.fail();
        };
    }
}
