package com.liuyun.model.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 密码登录请求参数类
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/26 18:32
 **/
@Data
@ApiModel(value = "密码登录请求参数类")
public class AuthPasswordLoginVO {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空!!!")
    @ApiModelProperty(value = "账号")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!!!")
    @ApiModelProperty(value = "密码")
    private String password;

}
