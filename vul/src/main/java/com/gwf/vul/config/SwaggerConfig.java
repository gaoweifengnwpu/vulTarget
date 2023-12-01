package com.gwf.vul.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("靶场")
                        .version("1.0")
                        .description( "靶场 RESTFul 接口文档说明")
                        .termsOfService("https://liushuijinger.blog.csdn.net")
                        .license(new License().name("Apache 2.0")
                                .url("https://liushuijinger.blog.csdn.net")));
    }



}

