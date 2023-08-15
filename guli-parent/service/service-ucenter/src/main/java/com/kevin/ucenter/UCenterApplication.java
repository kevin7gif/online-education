package com.kevin.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-05 8:50
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kevin"})// 扫描公共模块
@EnableDiscoveryClient
@MapperScan("com.kevin.ucenter.mapper")
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class, args);
    }
}
