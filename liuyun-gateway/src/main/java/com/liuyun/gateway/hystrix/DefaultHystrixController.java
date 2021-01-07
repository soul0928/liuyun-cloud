package com.liuyun.gateway.hystrix;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认降级处理
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/8 13:16
 **/
@Slf4j
@RestController
public class DefaultHystrixController {

    private static final String HYSTRIX_MSG = "系统繁忙, 请稍后再试!!!";

    @RequestMapping("/defaultFallback")
    public Result<Object> defaultFallback(){
        return Result.fail(GlobalResultEnum.SYSTEM_EXECUTION_TIMEOUT.getCode(), HYSTRIX_MSG);
    }

}
