package com.liuyun.user.modules.service.impl;

import com.liuyun.core.mybatisplus.service.impl.IBaseServiceImpl;
import com.liuyun.model.user.entity.SysRolePermissionEntity;
import com.liuyun.user.modules.mapper.SysRolePermissionMapper;
import com.liuyun.user.modules.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关联表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Service
public class SysRolePermissionServiceImpl
        extends IBaseServiceImpl<SysRolePermissionMapper, SysRolePermissionEntity> implements SysRolePermissionService {


}
