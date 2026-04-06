package com.taskapi.user.application.useCase.handler;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.taskapi.shared.application.port.out.security.IJwtConsumer;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.shared.domain.model.JwtSkeleton;
import com.taskapi.user.application.port.in.ILogInUserUseCase;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.application.useCase.command.LogInUserCommand;
import com.taskapi.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogInUserHandler implements ILogInUserUseCase {

    private final IUserRepository userRepository;

    private final IJwtConsumer jwtService;

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
