package com.taskapi.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.taskapi.security.infraestructure.config.SecurityModuleConfig;
import com.taskapi.shared.infraestructure.config.SharedKernelModuleConfig;
import com.taskapi.user.infraestructure.config.UserModuleConfig;
import com.taskapi.web.infraestructure.config.WebModuleConfig;


@Import({
	UserModuleConfig.class,
	SecurityModuleConfig.class,
	WebModuleConfig.class,
	SharedKernelModuleConfig.class
})
@SpringBootApplication
public class TaskAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAPIApplication.class, args);
	}

}
