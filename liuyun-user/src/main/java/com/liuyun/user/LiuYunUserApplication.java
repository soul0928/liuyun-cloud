package com.liuyun.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/3 17:56
 **/
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.liuyun.*"})
public class LiuYunUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiuYunUserApplication.class, args);
    }

}
