package com.liuyun.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyun.database.mybatisplus.mapper.IBaseMapper;
import com.liuyun.model.user.dto.SysUserPageDTO;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表 Mapper 接口
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:57
 */
@Mapper
public interface SysUserMapper extends IBaseMapper<SysUserEntity> {

    Page<SysUserPageVO> queryList(Page<SysUserPageVO> page, SysUserPageDTO sysUserPageDTO);
}
