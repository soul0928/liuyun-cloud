package com.liuyun.auth.mapper;

import com.liuyun.database.mybatisplus.mapper.AbstractMapper;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author WangDong
 * @since 2020-12-17 15:14:36
 */
@Mapper
public interface OauthClientDetailsMapper extends AbstractMapper<OauthClientDetailsEntity> {

}
