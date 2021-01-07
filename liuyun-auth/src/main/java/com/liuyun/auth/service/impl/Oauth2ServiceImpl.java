package com.liuyun.auth.service.impl;

import com.liuyun.auth.service.Oauth2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/4 17:30
 **/
@Slf4j
@Service
public class Oauth2ServiceImpl implements Oauth2Service {

    @Resource
    private TokenEndpoint tokenEndpoint;

}
