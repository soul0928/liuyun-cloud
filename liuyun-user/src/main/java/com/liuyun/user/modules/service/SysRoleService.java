package com.liuyun.user.modules.service;

import com.liuyun.core.mybatisplus.service.IBaseService;
import com.liuyun.model.user.entity.SysRoleEntity;

import java.util.Set;

/**
 * 基础角色表 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
public interface SysRoleService extends IBaseService<SysRoleEntity> {

    /**
     * 根据 用户ID 获取用户角色信息
     *
     * @param userId {@link Long} 用户ID
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2020/12/25 1:29 下午
     **/
    Set<String> getRolesByUserId(Long userId);
}

