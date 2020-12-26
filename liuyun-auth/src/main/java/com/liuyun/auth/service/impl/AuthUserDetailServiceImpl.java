package com.liuyun.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.liuyun.api.service.user.RoleFeignService;
import com.liuyun.api.service.user.UserFeignService;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.core.global.enums.GlobalResultEnum;
import com.liuyun.core.result.Result;
import com.liuyun.model.user.SysUserStatusEnum;
import com.liuyun.model.user.vo.SysUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:43
 **/
@Slf4j
@Component
public class AuthUserDetailServiceImpl implements AuthUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private RoleFeignService roleFeignService;

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
        Result<SysUserInfoVO> userResult = userFeignService.queryUserByUsername(username);
        log.info("根据用户账号获取用户信息, 调用 Feign 接口响应参数为 -> [{}]", JSONUtil.toJsonStr(userResult));
        if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), userResult.getCode())) {
            throw new InternalAuthenticationServiceException("用户信息不存在!!!");
        }
        SysUserInfoVO userInfoVO = userResult.getResult();
        if (Objects.isNull(userInfoVO)) {
            throw new InternalAuthenticationServiceException("用户信息不存在!!!");
        }
        checkUserStatus(userInfoVO);
        log.info("根据 用户ID 获取用户角色信息, 调用 Feign 接口 userId -> [{}]", userInfoVO.getId());
        Result<Set<String>> roleResult = roleFeignService.getRolesByUserId(userInfoVO.getId());
        log.info("根据 用户ID 获取用户角色信息, 调用 Feign 接口响应参数为 -> [{}]", JSONUtil.toJsonStr(roleResult));
        if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), roleResult.getCode())) {
            throw new InternalAuthenticationServiceException("用户信息不存在!!!");
        }
        Set<String> roles = roleResult.getResult();
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        return new User(username, passwordEncoder.encode(userInfoVO.getPassword()),  authorityList);
    }

    /**
     * 校验用户状态
     *
     * @param vo {@link SysUserInfoVO}
     * @author wangdong
     * @date 2020/12/25 10:37 上午
     **/
    private void checkUserStatus(SysUserInfoVO vo) {
        if (!Objects.equals(SysUserStatusEnum.ENABLE.getCode(), vo.getStatus())) {
            throw new InternalAuthenticationServiceException("该账号已禁用!!!");
        }
    }
}
