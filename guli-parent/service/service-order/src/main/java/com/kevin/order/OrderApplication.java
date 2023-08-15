package com.kevin.order;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-09 11:54
 */
@SpringBootApplication
@ComponentScan({"com.kevin"})
@EnableDiscoveryClient
@EnableFeignClients// 开启服务调用
@EnableSwaggerBootstrapUI
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
