package com.kevin.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置接口文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //创建Docket对象
    @Bean
    public Docket docket(){
        //1创建Docket对象
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        //2创建Api信息， 接口文档的总体描述
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("在线教育平台")
                .version("1.0")
                .description("前后端分离，前端Vue，后端Spring Boot + SpringCloudAlibaba的分布式项目")
                .contact(new Contact("kevin7gif","kevin7gif.github.io","2632176642@qq.com"))
                .build();

        //3.设置使用ApiInfo
        docket = docket.apiInfo(apiInfo);

        //4.设置参与文档生成的包
        docket = docket.select().apis(RequestHandlerSelectors.
                basePackage("com.kevin")).build();

        return docket;

    }
}
