package com.liuyun.auth.controller;

import com.liuyun.auth.service.OauthClientDetailsService;
import com.liuyun.database.mybatisplus.controller.IBaseController;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author WangDong
 * @since 2020-12-17 15:14:36
 */
@RestController
@RequestMapping("/oauthClientDetails")
@Api(tags = "服务")
public class OauthClientDetailsController extends IBaseController<OauthClientDetailsEntity> {
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

}
