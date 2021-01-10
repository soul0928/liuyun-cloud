package com.liuyun.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.liuyun.api.service.user.RoleFeignService;
import com.liuyun.api.service.user.UserFeignService;
import com.liuyun.auth.config.exception.AuthOauth2Exception;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.model.user.enums.SysUserStatusEnum;
import com.liuyun.model.user.vo.SysUserInfoVO;
import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:43
 **/
@Slf4j
@Component
public class AuthUserDetailServiceImpl implements AuthUserDetailsService {

   //  private static final String ROLE = "ROLE_";

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
        Result<SysUserInfoVO> usernameResult = userFeignService.queryUserByUsername(username);
        log.info("根据用户账号获取用户信息, 调用 Feign 接口响应参数为 -> [{}]", JSONUtil.toJsonStr(usernameResult));
        if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), usernameResult.getCode())) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_NOT_EXIST);
        }
        SysUserInfoVO userInfoVO = usernameResult.getResult();
        if (Objects.isNull(userInfoVO)) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_NOT_EXIST);
        }
        checkUserStatus(userInfoVO);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
        Set<String> roles = getRoles(userInfoVO.getId());
        if (CollectionUtil.isNotEmpty(roles)) {
            grantedAuthorities = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        }
        return new User(username, passwordEncoder.encode(userInfoVO.getPassword()),  grantedAuthorities);
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile {@link String} 手机号码
     * @author wangdong
     * @date 2021/1/10 8:30 下午
     * @return org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUserMobile(String mobile) {
        log.info("根据手机号获取用户信息, 调用 Feign 接口 mobile -> [{}]", mobile);
        Result<SysUserInfoVO> mobileResult = userFeignService.queryUserByUsername(mobile);
        log.info("根据手机号获取用户信息, 调用 Feign 接口响应参数为 -> [{}]", JSONUtil.toJsonStr(mobileResult));
        if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), mobileResult.getCode())) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_NOT_EXIST);
        }
        SysUserInfoVO userInfoVO = mobileResult.getResult();
        if (Objects.isNull(userInfoVO)) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_NOT_EXIST);
        }
        checkUserStatus(userInfoVO);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
        Set<String> roles = getRoles(userInfoVO.getId());
        if (CollectionUtil.isNotEmpty(roles)) {
            grantedAuthorities = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        }
        return new User(userInfoVO.getPhone(), passwordEncoder.encode(userInfoVO.getPassword()),  grantedAuthorities);
    }

    /**
     * 获取角色信息
     *
     * @param userId {@link Long} userId
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2020/12/28 12:07 上午
     **/
    private Set<String> getRoles(Long userId) {
        log.info("根据 用户ID 获取用户角色信息, 调用 Feign 接口 userId -> [{}]", userId);
        Result<Set<String>> roleResult = roleFeignService.getRolesByUserId(userId);
        log.info("根据 用户ID 获取用户角色信息, 调用 Feign 接口响应参数为 -> [{}]", JSONUtil.toJsonStr(roleResult));
        if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), roleResult.getCode())) {
            throw new InternalAuthenticationServiceException("用户信息不存在!!!");
        }
        Set<String> roles = roleResult.getResult();
        return Optional.ofNullable(roles).orElse(new HashSet<>());
        // return Optional.ofNullable(roles).orElse(new HashSet<>()).stream().map(i -> ROLE + i).collect(Collectors.toSet());
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
