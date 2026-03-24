package com.taskapi.security.application.port.in;

import java.util.Map;

import com.taskapi.security.application.useCase.command.LogInUserCommand;


public interface ILogInUserUseCase {
    Map<String,Object> execute(LogInUserCommand cmd);
}
