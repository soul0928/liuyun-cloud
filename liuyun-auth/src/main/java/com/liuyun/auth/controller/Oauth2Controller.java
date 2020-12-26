package com.liuyun.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.core.result.Result;
import com.liuyun.model.auth.vo.AuthPasswordLoginVO;
import com.liuyun.oauth2.constants.Oauth2Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 定义 oauth2 认证接口
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/26 18:22
 **/
@Slf4j
@RestController
@RequestMapping(value = "/oauth")
public class Oauth2Controller {

    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthClientDetailsService authClientDetailsService;
    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 密码登录
     *
     * @param vo {@link AuthPasswordLoginVO} 密码登录请求参数
     * @return com.liuyun.core.result.Result<java.lang.Object>
     * @author wangdong
     * @date 2020/12/26 6:27 下午
     **/
    @ApiModelProperty("密码登录")
    @PostMapping(value = Oauth2Constants.PASSWORD_LOGIN_URL)
    public void save(@Validated @RequestBody AuthPasswordLoginVO vo,
                               HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(vo.getUsername(), vo.getPassword());
        writerToken(request, response, token, "用户名或密码错误",1L);
    }

    private void writerToken(HttpServletRequest request, HttpServletResponse response,
                             AbstractAuthenticationToken token, String badCredenbtialsMsg, Long userId) throws IOException {
        try {
            String clientId = request.getHeader("client_id");
            String clientSecret = request.getHeader("client_secret");
            if (StrUtil.isBlank(clientId)) {
                throw new UnapprovedClientAuthenticationException("请求头中无client_id信息");
            }
            if (StrUtil.isBlank(clientSecret)) {
                throw new UnapprovedClientAuthenticationException("请求头中无client_secret信息");
            }

            ClientDetails clientDetails = getClient(clientId, clientSecret);
            TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, clientDetails.getScope(), "customer");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);

            writerObj(response, oAuth2AccessToken);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            exceptionHandler(response, badCredenbtialsMsg);
            e.printStackTrace();
        } catch (Exception e) {
            exceptionHandler(response, e);
        }
    }

    private ClientDetails getClient(String clientId, String clientSecret) {
        ClientDetails clientDetails = authClientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        return clientDetails;
    }

    private void writerObj(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(obj));
            writer.flush();
        }
    }

    private void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        log.error("exceptionHandler-error:", e);
        exceptionHandler(response, e.getMessage());
    }


    private void exceptionHandler(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        writerObj(response, Result.fail(msg));
    }
}
