package com.liuyun.user.modules.mapper;

import com.liuyun.core.mybatisplus.mapper.IBaseMapper;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 基础权限表 Mapper 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Mapper
public interface SysPermissionMapper extends IBaseMapper<SysPermissionEntity> {

    /**
     * 根据 用户ID 获取用户权限信息
     *
     * @param sysUserRoleEntity {@link SysUserRoleEntity}
     * @return java.util.Set<java.lang.String>
     * @author wangdong
     * @date 2020/12/28 12:31 上午
     **/
    Set<String> getPermissionsByUserId(SysUserRoleEntity sysUserRoleEntity);
}
