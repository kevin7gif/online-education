package com.kevin.acl;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-15 15:38
 */
@SpringBootApplication
@ComponentScan({"com.kevin"})
@EnableDiscoveryClient
@EnableSwaggerBootstrapUI
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
