package com.liuyun.model.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyun.database.mybatisplus.entity.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * 
 *
 * @author WangDong
 * @since 2020-12-17 15:14:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("OauthClientDetailsEntity")
@Accessors(chain = true)
@TableName("oauth_client_details")
@ApiModel(value="OauthClientDetailsEntity对象", description="")
public class OauthClientDetailsEntity extends IBaseEntity<OauthClientDetailsEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用标识")
    private String clientId;

    @ApiModelProperty(value = "应用名称")
    private String clientName;

    @ApiModelProperty(value = "资源限定串(逗号分割)")
    private String resourceIds;

    @ApiModelProperty(value = "应用密钥")
    private String clientSecret;

    @ApiModelProperty(value = "范围")
    private String scope;

    @ApiModelProperty(value = "5种oauth授权方式(authorization_code,password,refresh_token,client_credentials)")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向 uri")
    private String registeredRedirectUri;

    @ApiModelProperty(value = "权限")
    private String authorities;

    @ApiModelProperty(value = "access_token有效期 (秒)")
    private Integer accessTokenValiditySeconds;

    @ApiModelProperty(value = "refresh_token有效期(秒)")
    private Integer refreshTokenValiditySeconds;

    @ApiModelProperty(value = "附加信息")
    private String additionalInformation;

    @ApiModelProperty(value = "是否自动授权(true:是; false:否)")
    private Boolean autoapprove;


}
