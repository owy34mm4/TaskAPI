package com.taskapi.security.application.port.in;

import java.util.Map;

import com.taskapi.security.application.useCase.command.CreateUserCommand;


public interface ICreateUserUseCase {

    Map<String,Object> execute (CreateUserCommand cmd);
    
}
