package com.liuyun.model.user.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息表
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:56
 */
@Data
@Api(tags = "用户信息")
public class SysUserInfoVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐加密")
    private String salt;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "用户性别（0:男; 1:女; 2:未知）")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatarUrl;

    @ApiModelProperty(value = "帐号状态(100:启用; 200:禁用)")
    private Integer status;
}
