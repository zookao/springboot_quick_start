package com.zookao.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * User: zookao
 * Date: 2021-12-14
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        // return new Docket(DocumentationType.OAS_30)
        return new Docket(DocumentationType.SWAGGER_2)
                // .groupName("产研组")
                .apiInfo(apiInfo())
                .select()
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.zookao.admin.controller"))
                .paths(PathSelectors.any())
                .build();
                // .enable(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("开发者zookao")
                .contact(new Contact("zookao", "", "zookao@126.com"))
                .version("1.0")
                .build();
    }
}