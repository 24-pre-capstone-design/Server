package com.pre_capstone_design_24.server.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springBootAPI() {

        Info info = new Info()
                .title("SpringBoot Rest API Documentation")
                .description("2024년도 경기대 기초캡스톤 컴퓨터공학전공 BE api")
                .version("0.1");

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }

}