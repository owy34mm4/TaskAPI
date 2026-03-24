package com.taskapi.security.application.useCase.handler;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.taskapi.security.application.JwtService;
import com.taskapi.security.application.port.in.ILogInUserUseCase;
import com.taskapi.security.application.useCase.command.LogInUserCommand;
import com.taskapi.security.domain.JwtSkeleton;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogInUserHandler implements ILogInUserUseCase {

    private final IUserRepository userRepository;

    private final JwtService jwtService;

    private boolean usernameOrEmailNotAsociatedToAnyUser(String valueToCheck){
        return !userRepository.existsByUsernameOrEmail(valueToCheck);
    }


    @Override
    public Map<String,Object> execute(LogInUserCommand cmd) {
        if(usernameOrEmailNotAsociatedToAnyUser(cmd.getUsernameOrEmail())){throw new BussinesRuleException("UsernameOrEmail not associated");}
        
        User user = userRepository.findByUsernameOrEmail(cmd.getUsernameOrEmail());

        if(!user.verifyPassword(cmd.getPassword())){throw new BussinesRuleException("Credenciales Invalidas");}

        return Map.of(
            "token",jwtService.generateToken(JwtSkeleton.of(user.getId(), user.getEmail())),
            "userData",user
        );
    
    }


    
}
