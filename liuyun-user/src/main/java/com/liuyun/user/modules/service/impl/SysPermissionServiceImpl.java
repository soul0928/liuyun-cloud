package com.liuyun.user.modules.service.impl;

import com.liuyun.core.mybatisplus.service.impl.IBaseServiceImpl;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.user.modules.mapper.SysPermissionMapper;
import com.liuyun.user.modules.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * 基础权限表 服务实现类
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Service
public class SysPermissionServiceImpl
        extends IBaseServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {


}
