package com.liuyun.auth.config.mode.mobilepassword;


import com.liuyun.auth.config.exception.AuthOauth2Exception;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.utils.global.enums.GlobalResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户手机号密码验证
 *
 * @author wangdong
 * @date 2021/1/10 8:45 下午
 **/
@Slf4j
@Component
public class MobilePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobilePasswordAuthenticationToken authenticationToken = (MobilePasswordAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();

        UserDetails userDetails = authUserDetailsService.loadUserByUserMobile(mobile);
        if (Objects.isNull(userDetails)) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_NOT_EXIST);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthOauth2Exception(GlobalResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        MobilePasswordAuthenticationToken authenticationResult = new MobilePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
