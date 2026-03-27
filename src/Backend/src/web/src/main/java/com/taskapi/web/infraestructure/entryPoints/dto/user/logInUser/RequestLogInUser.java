package com.taskapi.web.infraestructure.entryPoints.dto.user.logInUser;

import com.taskapi.security.application.useCase.command.LogInUserCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestLogInUser {

    private String username_or_email;

    private String password;

    public LogInUserCommand toCommand(){
        return LogInUserCommand.builder()
            .usernameOrEmail(username_or_email)
            .password(password)
        .build();
    }
    
}
