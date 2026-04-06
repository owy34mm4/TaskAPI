package com.taskapi.task.infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.taskapi.task")
@ComponentScan(basePackages = "com.taskapi.task")
@EntityScan(basePackages = "com.taskapi.task")
public class TaskModuleConfig {
    
}
