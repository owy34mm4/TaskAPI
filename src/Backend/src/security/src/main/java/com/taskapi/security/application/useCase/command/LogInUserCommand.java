package com.taskapi.security.application.useCase.command;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LogInUserCommand {
    private String usernameOrEmail;

    private String password;
}
