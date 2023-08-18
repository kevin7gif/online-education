package com.kevin.gateway.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-18 10:11
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("*"); // 允许跨域的源，可以设置为 * 表示接受任何源
        corsConfig.addAllowedHeader("*"); // 允许跨域的请求头
        corsConfig.addAllowedMethod("*"); // 允许跨域的请求方法，如 GET、POST 等

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // 对所有路径都启用跨域配置

        return new CorsWebFilter(source);
    }
}
