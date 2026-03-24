package com.taskapi.security.infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.taskapi.security")
@EntityScan(basePackages = "com.taskapi.security")
public class SecurityModuleConfig {
    
}
