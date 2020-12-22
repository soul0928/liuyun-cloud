package com.liuyun.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.liuyun.auth.dto.AuthClientDetailsDTO;
import com.liuyun.auth.mapper.OauthClientDetailsMapper;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import com.liuyun.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 14:30
 **/
@Slf4j
@Component
public class AuthClientDetailsServiceImpl implements AuthClientDetailsService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return null;
    }

    @Override
    public void loadAllClientToCache() {
        List<OauthClientDetailsEntity> list = new LambdaQueryChainWrapper<>(this.oauthClientDetailsMapper)
                .eq(OauthClientDetailsEntity::getDelFlag, OauthClientDetailsEntity.DEL_FLAG_NORMAL)
                .list();
        Map<String, Object> map = new HashMap<>();
        list.forEach(i -> map.put(i.getClientId(), JSONUtil.toJsonStr(new AuthClientDetailsDTO(i))));
        redisService.hSetAll("liuyun:client", map);

        Object clientDetails = redisService.hGet("liuyun:client", "test");
        System.out.println(clientDetails);
    }
}
