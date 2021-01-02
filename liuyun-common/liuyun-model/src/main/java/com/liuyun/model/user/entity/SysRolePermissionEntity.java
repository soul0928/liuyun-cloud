package com.liuyun.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyun.database.mybatisplus.entity.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * 角色权限关联表
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("SysRolePermissionEntity")
@Accessors(chain = true)
@TableName("sys_role_permission")
@ApiModel(value="SysRolePermissionEntity对象", description="角色权限关联表")
public class SysRolePermissionEntity extends IBaseEntity<SysRolePermissionEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色 id")
    private Long roleId;

    @ApiModelProperty(value = "权限 id")
    private Long permissionId;


}
