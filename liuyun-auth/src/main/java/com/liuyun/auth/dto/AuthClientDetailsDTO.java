package com.liuyun.auth.dto;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/17 16:16
 **/
@Slf4j
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthClientDetailsDTO implements ClientDetails {

    private static final long serialVersionUID = 7524219233169301270L;

    private String clientId;

    private String clientSecret;

    private Set<String> scope = new HashSet<>();

    private Set<String> resourceIds = new HashSet<>();

    private Set<String> authorizedGrantTypes = new HashSet<>();

    private Set<String> registeredRedirectUris = new HashSet<>();

    private List<GrantedAuthority> authorities = new ArrayList();

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private Boolean autoapprove;

    private Map<String, Object> additionalInformation = Maps.newHashMap();

    public AuthClientDetailsDTO(OauthClientDetailsEntity entity) {
        this.setClientId(entity.getClientId());
        this.setClientSecret(entity.getClientSecret());
        if (StringUtils.isNotEmpty(entity.getScope())) {
            String[] scope = entity.getScope().split(",");
            this.setScope(Sets.newHashSet(scope));
        }
        if (StringUtils.isNotEmpty(entity.getResourceIds())) {
            String[] resourceIds = entity.getResourceIds().split(",");
            this.setResourceIds(Sets.newHashSet(resourceIds));
        }
        if (StringUtils.isNotEmpty(entity.getAuthorizedGrantTypes())) {
            String[] authorizedGrantTypes = entity.getAuthorizedGrantTypes().split(",");
            this.setAuthorizedGrantTypes(Sets.newHashSet(authorizedGrantTypes));
        }
        this.setRegisteredRedirectUris(Sets.newHashSet(entity.getRegisteredRedirectUri()));
        if (StringUtils.isNotEmpty(entity.getAuthorities())) {
            List<GrantedAuthority> list = new ArrayList<>();
            String[] split = entity.getAuthorities().split(",");
            for (String s : split) {
                list.add(new SimpleGrantedAuthority(s));
            }
            this.setAuthorities(list);
        }
        this.setAccessTokenValiditySeconds(entity.getAccessTokenValiditySeconds());
        this.setRefreshTokenValiditySeconds(entity.getRefreshTokenValiditySeconds());
        this.setAutoapprove(entity.getAutoapprove());
        Map map = JSONUtil.toBean(entity.getAdditionalInformation(), Map.class);
        this.setAdditionalInformation(map);
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isSecretRequired() {
        return Objects.nonNull(this.clientSecret);
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public boolean isScoped() {
        return CollectionUtil.isNotEmpty(this.scope);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return this.autoapprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    public static void main(String[] args) {
        OauthClientDetailsEntity oauthClientDetailsEntity = new OauthClientDetailsEntity();
        oauthClientDetailsEntity.setClientId("test");
        oauthClientDetailsEntity.setClientName("test");
        oauthClientDetailsEntity.setResourceIds("");
        oauthClientDetailsEntity.setClientSecret("asdasdsdasdasdsada");
        oauthClientDetailsEntity.setClientSecretStr("test");
        oauthClientDetailsEntity.setScope("all");
        oauthClientDetailsEntity.setAuthorizedGrantTypes("authorization_code,password");
        oauthClientDetailsEntity.setRegisteredRedirectUri("");
        oauthClientDetailsEntity.setAuthorities("");
        oauthClientDetailsEntity.setAccessTokenValiditySeconds(10);
        oauthClientDetailsEntity.setRefreshTokenValiditySeconds(1110);
        oauthClientDetailsEntity.setAdditionalInformation("{}");
        oauthClientDetailsEntity.setAutoapprove(false);

        AuthClientDetailsDTO authClientDetailsDTO = new AuthClientDetailsDTO(oauthClientDetailsEntity);
        ClientDetails clientDetails = (ClientDetails)authClientDetailsDTO;
        System.out.println(clientDetails);
    }
}
