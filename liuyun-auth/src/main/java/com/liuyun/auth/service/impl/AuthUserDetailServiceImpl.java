package com.liuyun.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.liuyun.api.service.user.UserFeignService;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.core.exception.GlobalException;
import com.liuyun.core.global.enums.GlobalResultEnum;
import com.liuyun.core.result.Result;
import com.liuyun.model.user.vo.SysUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:43
 **/
@Slf4j
@Component
public class AuthUserDetailServiceImpl implements AuthUserDetailsService {

    @Autowired
    private UserFeignService userFeignService;

    /**
     * 根据用户账号获取用户信息
     *
     * @param username {@link String} 账号
     * @return org.springframework.security.core.userdetails.UserDetails
     * @author wangdong
     * @date 2020/12/15 9:51 上午
     **/
    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("根据用户账号获取用户信息, 调用 Feign 接口 username -> [{}]", username);
        Result<SysUserInfoVO> result = userFeignService.queryUserByUsername(username);
        log.info("根据用户账号获取用户信息, 调用 Feign 接口响应参数为 -> [{}]", JSONUtil.toJsonStr(result));
        if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), result.getCode())) {
            throw new GlobalException();
        }
        return null;
    }
}
