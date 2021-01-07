package com.liuyun.user.mapper;

import com.liuyun.database.mybatisplus.mapper.IBaseMapper;
import com.liuyun.model.user.dto.AllRolePermissionDTO;
import com.liuyun.model.user.entity.SysPermissionEntity;
import com.liuyun.model.user.entity.SysRolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色权限关联表 Mapper 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Mapper
public interface SysRolePermissionMapper extends IBaseMapper<SysRolePermissionEntity> {

    /**
     * 查询所有角色关联权限信息
     *
     * @param sysPermissionEntity {@link SysPermissionEntity}
     * @return java.util.List<com.liuyun.model.user.dto.AllRolePermissionDTO>
     * @author wangdong
     * @date 2021/1/3 3:55 下午
     **/
    List<AllRolePermissionDTO> queryAllRolePermission(SysPermissionEntity sysPermissionEntity);
}
