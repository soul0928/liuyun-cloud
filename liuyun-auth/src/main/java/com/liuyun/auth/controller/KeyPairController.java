package com.liuyun.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/3 12:57
 **/
@Slf4j
@RestController
@Api(tags = "密钥服务")
@RequestMapping(value = "/keypair")
public class KeyPairController {

    @Autowired
    private KeyPair keyPair;

    @GetMapping(value = "/getPublicKey")
    @ApiOperation(value = "获取公钥")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
