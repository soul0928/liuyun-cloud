package com.liuyun.user.modules.mapper;

import com.liuyun.core.mybatisplus.mapper.IBaseMapper;
import com.liuyun.model.user.entity.SysPermissionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基础权限表 Mapper 接口
 *
 * @author WangDong
 * @since 2020-12-25 13:12:39
 */
@Mapper
public interface SysPermissionMapper extends IBaseMapper<SysPermissionEntity> {

}
