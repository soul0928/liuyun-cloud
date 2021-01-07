package com.liuyun.auth.config.utils;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Sets;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import com.liuyun.utils.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * ClientDetails 参数转换
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/17 16:16
 **/
public class AuthClientDetailsUtil {

    /**
     * 参数转换
     *
     * @param entity {@link OauthClientDetailsEntity}
     * @return org.springframework.security.oauth2.provider.ClientDetails
     * @author wangdong
     * @date 2020/12/24 4:22 下午
     **/
    public static ClientDetails getClientDetails(OauthClientDetailsEntity entity) {
        return new ClientDetails() {
            private static final long serialVersionUID = -7679007769145235146L;
            @Override
            public String getClientId() {
                return entity.getClientId();
            }
            @Override
            public Set<String> getResourceIds() {
                if (StringUtils.isNotEmpty(entity.getResourceIds())) {
                    String[] resourceIds = entity.getResourceIds().split(",");
                    return Sets.newHashSet(resourceIds);
                }
                return Sets.newHashSet();
            }
            @Override
            public boolean isSecretRequired() {
                return StringUtils.isNotEmpty(entity.getClientSecret());
            }

            @Override
            public String getClientSecret() {
                return entity.getClientSecret();
            }

            @Override
            public boolean isScoped() {
                return StringUtils.isNotEmpty(entity.getScope());
            }
            @Override
            public Set<String> getScope() {
                if (StringUtils.isNotEmpty(entity.getScope())) {
                    String[] scope = entity.getScope().split(",");
                    return Sets.newHashSet(scope);
                }
                return Sets.newHashSet();
            }
            @Override
            public Set<String> getAuthorizedGrantTypes() {
                if (StringUtils.isNotEmpty(entity.getAuthorizedGrantTypes())) {
                    String[] authorizedGrantTypes = entity.getAuthorizedGrantTypes().split(",");
                    return Sets.newHashSet(authorizedGrantTypes);
                }
                return Sets.newHashSet();
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                return Sets.newHashSet(entity.getRegisteredRedirectUri());
            }
            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                if (StringUtils.isNotEmpty(entity.getAuthorities())) {
                    List<GrantedAuthority> list = new ArrayList<>();
                    String[] split = entity.getAuthorities().split(",");
                    for (String s : split) {
                        list.add(new SimpleGrantedAuthority(s));
                    }
                    return list;
                }
                return new ArrayList<>();
            }
            @Override
            public Integer getAccessTokenValiditySeconds() {
                return entity.getAccessTokenValiditySeconds();
            }
            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return entity.getRefreshTokenValiditySeconds();
            }
            @Override
            public boolean isAutoApprove(String scope) {
                return entity.getAutoapprove();
            }
            @Override
            public Map<String, Object> getAdditionalInformation() {
                return JSONUtil.toBean(entity.getAdditionalInformation(), Map.class);
            }
        };
    }

}
