package com.liuyun.user.service;

import com.liuyun.database.mybatisplus.service.AbstractService;
import com.liuyun.model.user.entity.SysRolePermissionEntity;

import java.util.Set;

/**
 * 角色权限关联表 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
public interface SysRolePermissionService extends AbstractService<SysRolePermissionEntity> {

    /**
     * 获取路径需要角色信息
     *
     * @param url {@link String} 请求路径
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2021/1/3 7:04 下午
     **/
    Set<String> getRoleByUrl(String url);
}

