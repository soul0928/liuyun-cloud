package com.liuyun.gateway.service.fallback;

import com.liuyun.gateway.service.SysRolePermissionFeignService;
import com.liuyun.utils.result.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/3 17:39
 **/
@Slf4j
@Component
public class SysRolePermissionFeignServiceFallbackFactory  implements FallbackFactory<SysRolePermissionFeignService> {

    @Override
    public SysRolePermissionFeignService create(Throwable throwable) {
        return roleCodes -> {
            log.error("获取路径需要角色信息异常 -> [{}]", roleCodes, throwable);
            return Result.fail();
        };
    }

}
