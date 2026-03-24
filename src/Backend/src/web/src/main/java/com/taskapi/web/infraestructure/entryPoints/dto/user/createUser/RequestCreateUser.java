package com.taskapi.web.infraestructure.entryPoints.dto.user.createUser;

import com.taskapi.security.application.useCase.command.CreateUserCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateUser{

    private String name;

    private String username;

    private String password;

    private String email;

    public CreateUserCommand toCommand(){
        return CreateUserCommand.builder()
            .name(name)
            .email(email)
            .password(password)
            .username(username)
        .build();
    }
}