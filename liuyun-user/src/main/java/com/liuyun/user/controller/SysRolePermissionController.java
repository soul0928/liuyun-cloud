package com.liuyun.user.controller;

import com.liuyun.database.mybatisplus.controller.IBaseController;
import com.liuyun.model.user.entity.SysRolePermissionEntity;
import com.liuyun.user.service.SysRolePermissionService;
import com.liuyun.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * 角色权限关联表 前端控制器
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@RestController
@RequestMapping("/sysRolePermission")
@Api(tags = "角色权限关联表服务")
public class SysRolePermissionController extends IBaseController<SysRolePermissionEntity> {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 获取路径需要角色信息
     *
     * @param map {@link Map} 请求路径
     * @return com.liuyun.utils.result.Result<java.lang.Object>
     * @author wangdong
     * @date 2021/1/3 4:56 下午
     **/
    @PostMapping(value = "/getRoleByUrl")
    @ApiOperation(value = "加载角色权限信息")
    public Result<Set<String>> getRoleByUrl(@RequestBody Map<String, String> map) {
        Set<String> set = this.sysRolePermissionService.getRoleByUrl(map.get("url"));
        return Result.success(set);
    }

}
