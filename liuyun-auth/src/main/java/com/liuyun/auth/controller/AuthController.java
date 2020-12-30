package com.liuyun.auth.controller;

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
public class AuthController {

    @GetMapping("/auth/login")
    public String loginPage(Model model){
        model.addAttribute("loginProcessUrl", "/authentication/form");
        return "/login";
    }

    @RequestMapping("/auth/confirm_access")
    public String getAccessConfirmation(Map<String, Object> params, HttpServletRequest request, Model model) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) params.get("authorizationRequest");
        model.addAttribute("clientId", authorizationRequest.getClientId());
        model.addAttribute("scopes",authorizationRequest.getScope());
        return "/base";
    }

}
