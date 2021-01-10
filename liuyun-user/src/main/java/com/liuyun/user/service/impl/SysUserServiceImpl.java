package com.liuyun.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyun.database.mybatisplus.service.impl.AbstractServiceImpl;
import com.liuyun.model.user.dto.SysUserPageDTO;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserPageVO;
import com.liuyun.user.mapper.SysUserMapper;
import com.liuyun.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务实现类
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:57
 */
@Service
public class SysUserServiceImpl
        extends AbstractServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Page<SysUserPageVO> queryPage(SysUserPageDTO sysUserPageDTO) {
        Page<SysUserPageVO> page = new Page<>(sysUserPageDTO.getCurrPage(), sysUserPageDTO.getPageSize());
        return this.sysUserMapper.queryList(page, sysUserPageDTO);
    }
}

