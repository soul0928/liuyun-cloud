package com.liuyun.web.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * runner
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/27 15:55
 **/
@Slf4j
@Component
public class Runner implements ApplicationRunner {

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${spring.application.name}")
    private String name;

    @Override
    public void run(ApplicationArguments args) {
        log.info("服务: [{}] 环境: [{}] 时间:[{}] 已启动完成!", name, active,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
