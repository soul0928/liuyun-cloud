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
 * 用户角色关联表
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("SysUserRoleEntity")
@Accessors(chain = true)
@TableName("sys_user_role")
@ApiModel(value="SysUserRoleEntity对象", description="用户角色关联表")
public class SysUserRoleEntity extends IBaseEntity<SysUserRoleEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户 id")
    private Long userId;

    @ApiModelProperty(value = "角色 id")
    private Long roleId;


}
