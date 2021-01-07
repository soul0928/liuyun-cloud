package com.liuyun.user.controller;

import com.liuyun.database.mybatisplus.controller.IBaseController;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import com.liuyun.user.service.SysUserRoleService;
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
@RequestMapping("/sysUserRole")
@Api(tags = "用户角色关联表服务")
public class SysUserRoleController extends IBaseController<SysUserRoleEntity> {
    @Autowired
    private SysUserRoleService sysUserRoleService;

}
