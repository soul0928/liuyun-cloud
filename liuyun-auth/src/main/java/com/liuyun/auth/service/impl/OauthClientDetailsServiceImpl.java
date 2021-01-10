package com.liuyun.auth.service.impl;

import com.liuyun.auth.mapper.OauthClientDetailsMapper;
import com.liuyun.auth.service.OauthClientDetailsService;
import com.liuyun.database.mybatisplus.service.impl.AbstractServiceImpl;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author WangDong
 * @since 2020-12-17 15:14:36
 */
@Service
public class OauthClientDetailsServiceImpl
        extends AbstractServiceImpl<OauthClientDetailsMapper, OauthClientDetailsEntity> implements OauthClientDetailsService {


}
