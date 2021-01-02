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
 * 基础角色表
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("SysRoleEntity")
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel(value="SysRoleEntity对象", description="基础角色表")
public class SysRoleEntity extends IBaseEntity<SysRoleEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端(100:管理端; 200:客户端)")
    private Integer roleClient;

    @ApiModelProperty(value = "角色编码 (例: admin:user:queryList)")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "数据范围（100：全部数据权限 200：自定数据权限")
    private Integer roleScope;

    @ApiModelProperty(value = "排序")
    private Integer roleSort;

    @ApiModelProperty(value = "角色状态(100:启用; 200:禁用)")
    private Integer roleStatus;


}
