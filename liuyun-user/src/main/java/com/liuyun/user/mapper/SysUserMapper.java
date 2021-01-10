package com.liuyun.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyun.database.mybatisplus.mapper.AbstractMapper;
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
public interface SysUserMapper extends AbstractMapper<SysUserEntity> {

    /**
     * 测试分页
     *
     * @param page page
     * @param sysUserPageDTO sysUserPageDTO
     * @author wangdong
     * @date 2021/1/10 8:59 下午
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.liuyun.model.user.vo.SysUserPageVO>
     **/
    Page<SysUserPageVO> queryList(Page<SysUserPageVO> page, SysUserPageDTO sysUserPageDTO);
}
