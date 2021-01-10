package com.liuyun.user.service.impl;

import com.liuyun.database.mybatisplus.service.impl.AbstractServiceImpl;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import com.liuyun.user.mapper.SysUserRoleMapper;
import com.liuyun.user.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Service
public class SysUserRoleServiceImpl
        extends AbstractServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {


}
