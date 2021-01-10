package com.liuyun.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * LiuyunGatewayApplication
 *
 * @author wangdong
 * @date 2021/1/10 9:05 下午
 **/
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.liuyun.*"})
public class LiuyunGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiuyunGatewayApplication.class, args);
    }

}
