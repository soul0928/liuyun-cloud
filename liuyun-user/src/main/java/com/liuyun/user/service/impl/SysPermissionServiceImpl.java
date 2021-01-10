package com.liuyun.user.service.impl;

import com.liuyun.database.mybatisplus.enums.DelFlagEnum;
import com.liuyun.database.mybatisplus.service.impl.AbstractServiceImpl;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import com.liuyun.user.mapper.SysPermissionMapper;
import com.liuyun.user.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 基础权限表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Service
public class SysPermissionServiceImpl
        extends AbstractServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 根据 用户ID 获取用户权限信息
     *
     * @param userId {@link Long} 用户ID
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2020/12/28 12:31 上午
     **/
    @Override
    public Set<String> getPermissionsByUserId(Long userId) {
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setUserId(userId);
        sysUserRoleEntity.setDelFlag(DelFlagEnum.NOT_DELETED);
        return this.sysPermissionMapper.getPermissionsByUserId(sysUserRoleEntity);
    }
}
