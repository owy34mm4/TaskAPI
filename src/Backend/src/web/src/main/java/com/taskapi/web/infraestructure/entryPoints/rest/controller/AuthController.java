package com.taskapi.web.infraestructure.entryPoints.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.taskapi.user.application.port.in.ICreateUserUseCase;
import com.taskapi.user.application.port.in.ILogInUserUseCase;
import com.taskapi.web.infraestructure.entryPoints.dto.user.createUser.RequestCreateUser;
import com.taskapi.web.infraestructure.entryPoints.dto.user.createUser.ResponseCreateUser;
import com.taskapi.web.infraestructure.entryPoints.dto.user.logInUser.RequestLogInUser;
import com.taskapi.web.infraestructure.entryPoints.dto.user.logInUser.ResponseLogInUser;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ICreateUserUseCase createUserHandler;

    private final ILogInUserUseCase logInUserHanlder;


    @PostMapping("/register")
    public ResponseEntity<ResponseCreateUser> createUser(@RequestBody RequestCreateUser requestCreateUser) {
        var cmd = requestCreateUser.toCommand();
        
        var resultModel = createUserHandler.execute(cmd);

        ResponseCreateUser response = ResponseCreateUser.createFromData(resultModel.get("userData"),resultModel.get("token"));
        return ResponseEntity.status(201).body(response);
    }
    

    @PostMapping("/login")
    public ResponseEntity<ResponseLogInUser> LogIn(@RequestBody RequestLogInUser request) {
        var cmd = request.toCommand();
        
        var resultModel = logInUserHanlder.execute(cmd);

        ResponseLogInUser response = ResponseLogInUser.createFromData(resultModel.get("token"));
        return ResponseEntity.ok(response);
    }
    
    
}
