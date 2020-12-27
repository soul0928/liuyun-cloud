package com.liuyun.auth.controller;

import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/25 20:54
 **/
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping("/info/{id}")
    public Result<Long> info(@PathVariable("id") Long id) {
        return Result.success(id);
    }

    /**
     * @EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
     *
     * prePostEnabled = true
     *
     * @PreAuthorize 注解会在方法执行前进行验证
     * @PostAuthorize 注解会在方法执行后进行验证 如果方法没有返回值实际上等于开放权限控制
     *
     * securedEnabled = true
     *
     * @Secured 角色必须以ROLE_开头，不可省略
     *
     * jsr250Enabled
     *
     * @DenyAll 拒绝所有访问
     * @RolesAllowed({"USER", "ADMIN"}) 该方法只要具有"USER", "ADMIN"任意一种权限就可以访问。这里可以省略前缀ROLE_，实际的权限可能是ROLE_ADMIN
     * @PermitAll 允许所有访问
     *
     **/

    /**
     * 测试   ROLE_admin   基于角色
     **/
    @GetMapping("/info1/{id}")
    @PreAuthorize("hasRole('admin')")
    public Result<Long> info1(@PathVariable("id") Long id) {
        log.info(id + "");
        return Result.success(id);
    }

    /**
     * 测试   test:info1  基于权限
     **/
    @GetMapping("/info2/{id}")
    @PreAuthorize("hasAuthority('test:info1')")
    public Result<Long> info2(@PathVariable("id") Long id) {
        log.info(id + "");
        return Result.success(id);
    }

    /**
     * 测试   ROLE_admin   基于角色
     **/
    @GetMapping("/info3/{id}")
    @PostAuthorize("hasRole('admin1')")
    public Result<Long> info3(@PathVariable("id") Long id) {
        log.info(id + "");
        return Result.success(id);
    }

    /**
     * 测试   ROLE_admin   基于角色
     **/
    @GetMapping("/info4/{id}")
    @DenyAll
    public Result<Long> info4(@PathVariable("id") Long id) {
        log.info(id + "");
        return Result.success(id);
    }

    /**
     * 测试   ROLE_admin   基于角色
     **/
    @GetMapping("/info5/{id}")
    @PermitAll
    public Result<Long> info5(@PathVariable("id") Long id) {
        log.info(id + "");
        return Result.success(id);
    }

}
