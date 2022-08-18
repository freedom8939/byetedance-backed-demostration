package com.bytedance.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置
 *
 * https://doc.xiaominfo.com/knife4j/documentation/get_start.html
 *
 */
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("springboot-init")
                        .description("springboot-init")
                        .termsOfServiceUrl("https://github.com/freedom8939/bytedance-backend")
                        .contact(new Contact("Ht,lanshuqian", "https://github.com/freedom8939/bytedance-backend",
                                "89398368@qq.com"))
                        .version("1.0v")
                        .build())
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.bytedance.project.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}