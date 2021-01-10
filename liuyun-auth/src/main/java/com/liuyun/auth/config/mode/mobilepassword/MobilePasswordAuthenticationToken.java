package com.liuyun.auth.config.mode.mobilepassword;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 机号密码认证令牌
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/10 20:07
 **/
public class MobilePasswordAuthenticationToken  extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;

    public MobilePasswordAuthenticationToken(Object mobile, Object password) {
        super(null);
        this.principal = mobile;
        this.credentials = password;
        super.setAuthenticated(false);
    }

    public MobilePasswordAuthenticationToken(Object mobile, Object password,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = mobile;
        this.credentials = password;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
