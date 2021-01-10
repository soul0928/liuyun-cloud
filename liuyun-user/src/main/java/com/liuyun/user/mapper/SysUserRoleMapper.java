package com.liuyun.user.mapper;

import com.liuyun.database.mybatisplus.mapper.AbstractMapper;
import com.liuyun.model.user.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表 Mapper 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Mapper
public interface SysUserRoleMapper extends AbstractMapper<SysUserRoleEntity> {

}
