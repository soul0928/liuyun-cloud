package com.liuyun.user.modules.controller;

import com.liuyun.core.mybatisplus.controller.IBaseController;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import com.liuyun.user.modules.service.SysUserRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色关联表 前端控制器
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39

 */
@RestController
@RequestMapping("/sysUserRoleEntity")
@Api(tags = "用户角色关联表服务")
public class SysUserRoleController extends IBaseController<SysUserRoleEntity> {
    @Autowired
    private SysUserRoleService sysUserRoleService;

}
