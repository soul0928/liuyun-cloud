package com.liuyun.auth.controller;

import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
