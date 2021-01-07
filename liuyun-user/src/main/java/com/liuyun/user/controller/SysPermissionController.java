package com.liuyun.user.controller;

import com.liuyun.database.mybatisplus.controller.IBaseController;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.user.service.SysPermissionService;
import com.liuyun.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 基础权限表 前端控制器
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39

 */
@RestController
@RequestMapping("/sysPermission")
@Api(tags = "基础权限表服务")
public class SysPermissionController extends IBaseController<SysPermissionEntity> {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 根据 用户ID 获取用户权限信息
     *
     * @param userId {@link Long} 用户ID
     * @return com.liuyun.core.result.Result<java.util.Set < java.lang.String>>
     * @author wangdong
     * @date 2020/12/25 1:20 下午
     **/
    @GetMapping("/getPermissionsByUserId/{userId}")
    @ApiOperation("根据 用户ID 获取用户权限信息")
    public Result<Set<String>> getPermissionsByUserId(@ApiParam(value = "用户ID", required = true) @PathVariable("userId") Long userId) {
        Set<String> set = this.sysPermissionService.getPermissionsByUserId(userId);
        return Result.success(Optional.ofNullable(set).orElse(new HashSet<>(1)));
    }
}
