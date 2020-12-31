package com.liuyun.auth.controller;

import com.liuyun.auth.config.constants.AuthConstants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * index html
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/29 22:02
 **/
@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
@Api(tags = "登录授权服务接口")
public class AuthController {

    /**
     * 跳转登录页面
     *
     * @param model {@link Model}
     * @return java.lang.String
     * @author wangdong
     * @date 2020/12/31 4:40 下午
     **/
    @GetMapping(AuthConstants.LOGIN_URL)
    public String loginPage(Model model){
        return "/login";
    }

    /**
     * 跳转授权页面
     *
     * @param params {@link Map}
     * @param request {@link HttpServletRequest}
     * @param model {@link Model}
     * @return java.lang.String
     * @author wangdong
     * @date 2020/12/31 4:40 下午
     **/
    @RequestMapping(AuthConstants.CONFIRM_ACCESS_URL)
    public String getAccessConfirmation(Map<String, Object> params, HttpServletRequest request, Model model) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) params.get("authorizationRequest");
        model.addAttribute("clientId", authorizationRequest.getClientId());
        model.addAttribute("scopes",authorizationRequest.getScope());
        return "/base";
    }

}
