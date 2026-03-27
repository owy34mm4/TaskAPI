package com.taskapi.user.application.port.in;

import java.util.Map;

import com.taskapi.user.application.useCase.command.LogInUserCommand;


public interface ILogInUserUseCase {
    Map<String,Object> execute(LogInUserCommand cmd);
}
