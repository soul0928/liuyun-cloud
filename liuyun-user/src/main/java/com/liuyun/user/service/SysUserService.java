package com.liuyun.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyun.database.mybatisplus.service.AbstractService;
import com.liuyun.model.user.dto.SysUserPageDTO;
import com.liuyun.model.user.entity.SysUserEntity;
import com.liuyun.model.user.vo.SysUserPageVO;

/**
 * 用户信息表 接口
 *
 * @author WangDong
 * @since 2020年12月7日 下午2:11:57
 */
public interface SysUserService extends AbstractService<SysUserEntity> {

    /**
     * 测试分页
     *
     * @param sysUserPageDTO sysUserPageDTO
     * @author wangdong
     * @date 2021/1/10 8:59 下午
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.liuyun.model.user.vo.SysUserPageVO>
     **/
    Page<SysUserPageVO> queryPage(SysUserPageDTO sysUserPageDTO);
}

