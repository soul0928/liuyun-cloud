package com.liuyun.user.modules.controller;

import com.liuyun.core.mybatisplus.controller.IBaseController;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.user.modules.service.SysPermissionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础权限表 前端控制器
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39

 */
@RestController
@RequestMapping("/sysPermissionEntity")
@Api(tags = "基础权限表服务")
public class SysPermissionController extends IBaseController<SysPermissionEntity> {
    @Autowired
    private SysPermissionService sysPermissionService;

}
