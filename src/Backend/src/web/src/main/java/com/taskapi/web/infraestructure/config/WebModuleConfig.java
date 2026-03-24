package com.taskapi.web.infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.taskapi.web")
@EntityScan(basePackages = "com.taskapi.web")
public class WebModuleConfig {
    
}
