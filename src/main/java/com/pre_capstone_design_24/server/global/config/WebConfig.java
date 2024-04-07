package com.pre_capstone_design_24.server.global.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
@Slf4j

public class WebConfig implements WebMvcConfigurer {

    @Value("${resource.file.path}")
    private String filePath;

    @Value("${resource.file.url}")
    private String fileURL;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileURL + "/**")
                .addResourceLocations("file:" + File.separator + filePath + "/")
                .resourceChain(true);
    }

}
