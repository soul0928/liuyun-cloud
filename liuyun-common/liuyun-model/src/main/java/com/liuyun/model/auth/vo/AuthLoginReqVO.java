package com.liuyun.model.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录请求参数
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/5 11:16
 **/
@Data
@ApiModel(value = "登录请求参数")
public class AuthLoginReqVO {

    @ApiModelProperty(value = "授权类型", example = "password", required = true)
    private String grant_type;

    @ApiModelProperty(value = "登录账号", example = "admin")
    private String username;

    @ApiModelProperty(value = "登录密码", example = "admin")
    private String password;

    @ApiModelProperty(value = "refresh_token", example = "refresh_token")
    private String refreshToken;

    @ApiModelProperty(value = "授权码", example = "授权码")
    private String code;

    @ApiModelProperty(value = "重定向地址", example = "http://www.liuyum.com")
    private String redirectUri;
}
