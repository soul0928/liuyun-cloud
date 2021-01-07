package com.liuyun.user.mapper;

import com.liuyun.database.mybatisplus.mapper.IBaseMapper;
import com.liuyun.model.user.entity.SysRoleEntity;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 基础角色表 Mapper 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Mapper
public interface SysRoleMapper extends IBaseMapper<SysRoleEntity> {

    /**
     * 根据 用户ID 获取用户角色信息
     *
     * @param sysUserRoleEntity {@link SysUserRoleEntity}
     * @return java.util.List<java.lang.String>
     * @author wangdong
     * @date 2020/12/25 1:31 下午
     **/
    Set<String> getRolesByUserId(SysUserRoleEntity sysUserRoleEntity);
}
