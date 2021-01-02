package com.liuyun.api.service.user;

import com.liuyun.api.service.user.fallback.UserFeignServiceFallbackFactory;
import com.liuyun.model.user.vo.SysUserInfoVO;
import com.liuyun.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:19
 **/
@FeignClient(value = "liuyun-user",
        path = "/sysUser",
        fallbackFactory = UserFeignServiceFallbackFactory.class,
        contextId = "userFeignService",
        decode404 = true)
public interface UserFeignService {

    /**
     * 根据用户 id 获取用户信息
     *
     * @param id {@link Long} 用户 Id
     * @return com.liuyun.core.result.Result<com.liuyun.model.user.vo.SysUserInfoVO>
     * @author wangdong
     * @date 2020/12/14 3:38 下午
     **/
    @GetMapping("/queryUserById/{id}")
    Result<SysUserInfoVO> queryUserById(@PathVariable("id") Long id);

    /**
     * 根据用户账号获取用户信息
     *
     * @param username {@link String} 用户账号
     * @return com.liuyun.core.result.Result<com.liuyun.model.user.vo.SysUserInfoVO>
     * @author wangdong
     * @date 2020/12/14 3:38 下午
     **/
    @GetMapping("/queryUserByUsername/{username}")
    Result<SysUserInfoVO> queryUserByUsername(@PathVariable("username") String username);
}
