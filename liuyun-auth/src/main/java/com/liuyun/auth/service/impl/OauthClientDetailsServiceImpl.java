package com.liuyun.auth.service.impl;

import com.liuyun.auth.mapper.OauthClientDetailsMapper;
import com.liuyun.auth.service.OauthClientDetailsService;
import com.liuyun.core.mybatisplus.service.impl.IBaseServiceImpl;
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
        extends IBaseServiceImpl<OauthClientDetailsMapper, OauthClientDetailsEntity> implements OauthClientDetailsService {


}
