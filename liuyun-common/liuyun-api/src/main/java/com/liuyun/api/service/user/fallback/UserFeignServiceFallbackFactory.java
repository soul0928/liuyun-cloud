package com.liuyun.api.service.user.fallback;

import com.liuyun.api.service.user.UserFeignService;
import com.liuyun.core.result.Result;
import com.liuyun.model.user.vo.SysUserInfoVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:23
 **/
@Slf4j
@Component
public class UserFeignServiceFallbackFactory implements FallbackFactory<UserFeignService> {

    @Override
    public UserFeignService create(Throwable throwable) {
        return new UserFeignService() {
            @Override
            public Result<SysUserInfoVO> queryUserById(Long id) {
                log.error("通过用户Id查询用户异常 -> [{}]", id, throwable);
                return Result.fail();
            }

            @Override
            public Result<SysUserInfoVO> queryUserByUsername(String username) {
                log.error("根据用户账号获取用户信息 -> [{}]", username, throwable);
                return Result.fail();
            }
        };

    }
}
