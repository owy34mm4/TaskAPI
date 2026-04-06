package com.taskapi.user.application.port.in;

import java.util.Map;

import com.taskapi.user.application.useCase.command.CreateUserCommand;


public interface ICreateUserUseCase {

    Map<String,Object> execute (CreateUserCommand cmd);
    
}
