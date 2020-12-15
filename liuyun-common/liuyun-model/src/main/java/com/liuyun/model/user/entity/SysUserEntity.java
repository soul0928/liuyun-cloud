package com.liuyun.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyun.core.mybatisplus.entity.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * 用户信息表
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("SysUserEntity")
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUserEntity对象", description="用户信息表")
public class SysUserEntity extends IBaseEntity<SysUserEntity> {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private Integer sex;

    @ApiModelProperty(value = "头像路径")
    private String avatarUrl;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private Integer status;


}
