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
 * 基础权限表
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("SysPermissionEntity")
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="SysPermissionEntity对象", description="基础权限表")
public class SysPermissionEntity extends IBaseEntity<SysPermissionEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端(100:管理端; 200:客户端)")
    private Integer permissionClient;

    @ApiModelProperty(value = "权限编码 (例: admin:user:queryList)")
    private String permissionCode;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "权限描述")
    private String permissionDesc;

    @ApiModelProperty(value = "资源URI")
    private String permissionUri;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "权限类型(100:菜单; 200:按钮; 300:接口)")
    private Integer permissionType;

    @ApiModelProperty(value = "排序")
    private Integer permissionSort;

    @ApiModelProperty(value = "权限状态(100:启用; 200:禁用)")
    private Integer permissionStatus;


}
