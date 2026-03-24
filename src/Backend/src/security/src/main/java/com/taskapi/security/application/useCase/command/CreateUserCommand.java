package com.taskapi.security.application.useCase.command;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CreateUserCommand {
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private boolean active;
}
