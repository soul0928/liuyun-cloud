package com.liuyun.user.modules.controller;

import com.liuyun.core.mybatisplus.controller.IBaseController;
import com.liuyun.model.user.entity.SysRolePermissionEntity;
import com.liuyun.user.modules.service.SysRolePermissionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色权限关联表 前端控制器
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39

 */
@RestController
@RequestMapping("/sysRolePermissionEntity")
@Api(tags = "角色权限关联表服务")
public class SysRolePermissionController extends IBaseController<SysRolePermissionEntity> {
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

}
