package com.liuyun.user.service.impl;

import com.liuyun.database.mybatisplus.enums.DelFlagEnum;
import com.liuyun.database.mybatisplus.service.impl.AbstractServiceImpl;
import com.liuyun.model.user.entity.SysRoleEntity;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import com.liuyun.user.mapper.SysRoleMapper;
import com.liuyun.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 基础角色表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Service
public class SysRoleServiceImpl
        extends AbstractServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据 用户ID 获取用户角色信息
     *
     * @param userId {@link Long} 用户ID
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2020/12/25 1:29 下午
     **/
    @Override
    public Set<String> getRolesByUserId(Long userId) {
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setUserId(userId);
        sysUserRoleEntity.setDelFlag(DelFlagEnum.NOT_DELETED);
        return this.sysRoleMapper.getRolesByUserId(sysUserRoleEntity);
    }
}
