package com.kevin.statistics;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-14 9:37
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kevin"})
@EnableFeignClients // 开启服务调用
@EnableScheduling // 开启定时任务
@EnableSwaggerBootstrapUI
public class DailyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class, args);
    }
}
