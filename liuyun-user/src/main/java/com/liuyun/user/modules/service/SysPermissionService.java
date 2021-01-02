package com.liuyun.user.modules.service;

import com.liuyun.database.mybatisplus.service.IBaseService;
import com.liuyun.model.user.entity.SysPermissionEntity;

import java.util.Set;

/**
 * 基础权限表 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
public interface SysPermissionService extends IBaseService<SysPermissionEntity> {


    /**
     * 根据 用户ID 获取用户权限信息
     *
     * @param userId {@link Long} 用户ID
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2020/12/28 12:31 上午
     **/
    Set<String> getPermissionsByUserId(Long userId);
}

