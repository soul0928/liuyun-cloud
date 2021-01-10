package com.liuyun.auth.controller;

import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.io.IoUtil;
import com.liuyun.auth.config.constants.AuthConstants;
import com.liuyun.auth.config.exception.AuthOauth2Exception;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.auth.service.AuthRedisCodeService;
import com.liuyun.model.auth.vo.AuthLoginReqVO;
import com.liuyun.redis.constants.AuthRedisConstants;
import com.liuyun.redis.service.RedisService;
import com.liuyun.utils.captcha.CaptchaUtils;
import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.lang.StringUtils;
import com.liuyun.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;

/**
 * 登录授权服务接口
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/29 22:02
 **/
@Slf4j
@RestController
@RequestMapping(value = "/oauth")
@Api(tags = "登录授权服务接口")
public class Oauth2Controller {

    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private RedisService redisService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthRedisCodeService authRedisCodeService;
    @Autowired
    private AuthClientDetailsService authClientDetailsService;
    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 获取验证码
     *
     * @param uniqueCode {@link String} 唯一编码
     * @param response {@link HttpServletResponse}
     * @author wangdong
     * @date 2021/1/10 7:26 下午
     **/
    @GetMapping(value = "/captcha/{uniqueCode}")
    @ApiOperation(value = "获取验证码")
    public void getCaptcha(@ApiParam(value = "唯一编码", required = true) @PathVariable("uniqueCode") String uniqueCode,
                           HttpServletResponse response) {
        ShearCaptcha captcha = CaptchaUtils.createShearCaptcha();
        String cacheKey = AuthRedisConstants.getKey(AuthRedisConstants.AUTH_PREFIX, AuthRedisConstants.CAPTCHA_CODE_PREFIX, uniqueCode);
        this.redisService.set(cacheKey, captcha.getCode());
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            captcha.write(out);
        } catch (IOException e) {
            log.error("响应验证码异常", e);
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 重写 TokenEndpoint postAccessToken
     * 因为认证服务器没有开启表单认证 请求不会被 ClientCredentialsTokenEndpointFilter 拦截
     * 请求到这里之前会被 BasicAuthenticationFilter 拦截 进行客户端校验
     * 所以 principal 会拿到 client 认证信息
     *
     * @param principal Client {@link Principal} client 认证信息
     * @param vo        {@link AuthLoginReqVO} 认证请求参数
     * @return com.liuyun.utils.result.Result<org.springframework.security.oauth2.common.OAuth2AccessToken>
     * @author wangdong
     * @date 2021/1/6 3:41 下午
     **/
    @PostMapping(value = "/token")
    @ApiOperation(value = "获取token")
    public Result<OAuth2AccessToken> login(Principal principal, AuthLoginReqVO vo) {
        String clientId = getClientId(principal);
        ClientDetails clientDetails = authClientDetailsService.loadClientByClientId(clientId);
        Set<String> grantTypes = clientDetails.getAuthorizedGrantTypes();
        if (!grantTypes.contains(vo.getGrantType())) {
            return Result.fail(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), "该客户端暂不支持该认证类型");
        }
        OAuth2AccessToken auth2AccessToken;
        switch (vo.getGrantType()) {
            case AuthConstants.AUTHORIZATION_CODE:
                auth2AccessToken = authorizationCode(clientDetails, vo);
                break;
            case AuthConstants.CLIENT_CREDENTIALS:
                auth2AccessToken = clientCredentials(clientDetails, vo);
                break;
            case AuthConstants.PASSWORD:
                auth2AccessToken = loginByUsernamePassword(clientDetails, vo);
                break;
            case AuthConstants.REFRESH_TOKEN:
                auth2AccessToken = refreshToken(clientDetails, vo);
                break;
            default:
                return Result.fail(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), "暂不支持该认证类型");
        }
        return Result.success(auth2AccessToken);
    }

    /**
     * 授权码模式认证
     *
     * @param clientDetails {@link ClientDetails} 客户端信息
     * @param vo            {@link AuthLoginReqVO} 认证请求参数
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @author wangdong
     * @date 2021/1/8 2:30 下午
     **/
    private OAuth2AccessToken authorizationCode(ClientDetails clientDetails, AuthLoginReqVO vo) {
        try {
            TokenRequest tokenRequest = new TokenRequest(null, clientDetails.getClientId(), clientDetails.getScope(), vo.getGrantType());
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            // 缓存的待处理信息
            OAuth2Authentication storedAuth = this.authRedisCodeService.consumeAuthorizationCode(vo.getCode());
            OAuth2Request pendingOauth2Request = storedAuth.getOAuth2Request();
            Authentication userAuthentication = storedAuth.getUserAuthentication();
            String pendingClientId = pendingOauth2Request.getClientId();
            String pendingRedirectUri = pendingOauth2Request.getRequestParameters().get(OAuth2Utils.REDIRECT_URI);
            if (!Objects.equals(pendingClientId, clientDetails.getClientId())) {
                throw new AuthOauth2Exception(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), "客户端ID不匹配");
            }
            if (!Objects.equals(pendingRedirectUri, vo.getRedirectUri())) {
                throw new AuthOauth2Exception(GlobalResultEnum.USER_REQUEST_PARAM_ERROR.getCode(), "redirect_uri 错误");
            }
            OAuth2Authentication oauth2Authentication = this.createOauth2Authentication(oAuth2Request, userAuthentication);
            oauth2Authentication.setAuthenticated(true);
            return authorizationServerTokenServices.createAccessToken(oauth2Authentication);
        } catch (Exception e) {
            // 用户密码认证失败
            throw new AuthOauth2Exception(GlobalResultEnum.USER_REQUEST_PARAM_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 客户端模式认证
     *
     * @param clientDetails {@link ClientDetails} 客户端信息
     * @param vo            {@link AuthLoginReqVO} 认证请求参数
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @author wangdong
     * @date 2021/1/8 2:26 下午
     **/
    private OAuth2AccessToken clientCredentials(ClientDetails clientDetails, AuthLoginReqVO vo) {
        try {
            TokenRequest tokenRequest = new TokenRequest(null, clientDetails.getClientId(), clientDetails.getScope(), vo.getGrantType());
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oauth2Authentication = this.createOauth2Authentication(oAuth2Request, null);
            oauth2Authentication.setAuthenticated(true);
            OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oauth2Authentication);
            DefaultOAuth2AccessToken noRefresh = new DefaultOAuth2AccessToken(accessToken);
            noRefresh.setRefreshToken(null);
            return noRefresh;
        } catch (Exception e) {
            // 用户密码认证失败
            throw new AuthOauth2Exception(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 通过 RefreshToken 刷新 AccessToken
     *
     * @param clientDetails {@link ClientDetails} 客户端信息
     * @param vo            {@link AuthLoginReqVO} 认证请求参数
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @author wangdong
     * @date 2021/1/7 10:24 上午
     **/
    private OAuth2AccessToken refreshToken(ClientDetails clientDetails, AuthLoginReqVO vo) {
        try {
            OAuth2RefreshToken oAuth2RefreshToken = tokenStore.readRefreshToken(vo.getRefreshToken());
            OAuth2Authentication oAuth2Authentication = tokenStore.readAuthenticationForRefreshToken(oAuth2RefreshToken);
            OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
            String clientId = oAuth2Request.getClientId();
            if (!Objects.equals(clientId, clientDetails.getClientId())) {
                throw new AuthOauth2Exception(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), "客户端ID不匹配");
            }
            // 构建获取 Token 请求参数
            TokenRequest tokenRequest = new TokenRequest(null, clientDetails.getClientId(), clientDetails.getScope(), vo.getGrantType());
            return authorizationServerTokenServices.refreshAccessToken(vo.getRefreshToken(), tokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码模式认证
     *
     * @param clientDetails {@link ClientDetails} 客户端信息
     * @param vo            {@link AuthLoginReqVO} 认证请求参数
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @author wangdong
     * @date 2021/1/8 2:28 下午
     **/
    private OAuth2AccessToken loginByUsernamePassword(ClientDetails clientDetails, AuthLoginReqVO vo) {
        if (StringUtils.isBlank(vo.getUsername())) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_REQUEST_PARAM_IS_BLANK.getCode(), "请求登录账号不能空");
        }
        if (StringUtils.isBlank(vo.getPassword())) {
            throw new AuthOauth2Exception(GlobalResultEnum.USER_REQUEST_PARAM_IS_BLANK.getCode(), "请求登录密码不能空");
        }

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(vo.getUsername(), vo.getPassword());
            TokenRequest tokenRequest = new TokenRequest(null, clientDetails.getClientId(), clientDetails.getScope(), vo.getGrantType());
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            // Session
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = this.createOauth2Authentication(oAuth2Request, authentication);
            oAuth2Authentication.setAuthenticated(true);
            return authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        } catch (BadCredentialsException be) {
            // 用户密码认证失败
            throw new AuthOauth2Exception(GlobalResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }


    }

    /**
     * 获取经过 Basic 认证后的 clientId
     *
     * @param principal {@link Principal} client 认证信息
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @author wangdong
     * @date 2021/1/8 2:26 下午
     **/
    protected String getClientId(Principal principal) {
        if (!(principal instanceof Authentication)) {
            throw new AuthOauth2Exception(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), "客户端未通过身份验证");
        }
        Authentication client = (Authentication) principal;
        if (!client.isAuthenticated()) {
            throw new AuthOauth2Exception(GlobalResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), "客户端未通过身份验证");
        }
        String clientId = client.getName();
        if (client instanceof OAuth2Authentication) {
            clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
        }
        return clientId;
    }

    /**
     * 构建 Oauth2 认证信息
     *
     * @param storedRequest  {@link OAuth2Request} 请求认证参数
     * @param authentication {@link Authentication} 认证方式
     * @return org.springframework.security.oauth2.provider.OAuth2Authentication
     * @author wangdong
     * @date 2021/1/7 1:49 下午
     **/
    private OAuth2Authentication createOauth2Authentication(OAuth2Request storedRequest, Authentication authentication) {
        return new OAuth2Authentication(storedRequest, authentication);
    }

}
