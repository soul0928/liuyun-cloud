package com.liuyun.user.modules.service.impl;

import com.liuyun.core.mybatisplus.service.impl.IBaseServiceImpl;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import com.liuyun.user.modules.mapper.SysUserRoleMapper;
import com.liuyun.user.modules.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Service
public class SysUserRoleServiceImpl
        extends IBaseServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {


}
