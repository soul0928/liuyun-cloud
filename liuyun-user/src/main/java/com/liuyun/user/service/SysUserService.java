package com.liuyun.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyun.database.mybatisplus.service.IBaseService;
import com.liuyun.model.user.dto.SysUserPageDTO;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserPageVO;

/**
 * 用户信息表 接口
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:57
 */

public interface SysUserService extends IBaseService<SysUserEntity> {


    Page<SysUserPageVO> queryPage(SysUserPageDTO sysUserPageDTO);
}

