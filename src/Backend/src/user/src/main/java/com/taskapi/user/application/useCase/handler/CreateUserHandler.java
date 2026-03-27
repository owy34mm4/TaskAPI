package com.taskapi.user.application.useCase.handler;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.taskapi.shared.application.port.out.security.IJwtConsumer;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.shared.domain.model.JwtSkeleton;
import com.taskapi.user.application.port.in.ICreateUserUseCase;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.application.useCase.command.CreateUserCommand;
import com.taskapi.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserHandler implements ICreateUserUseCase {

    private final IUserRepository userRepository;

    private final IJwtConsumer jwtService;

    @Override
    public Map<String,Object> execute(CreateUserCommand cmd) {
        if (userRepository.existsByEmail(cmd.getEmail())) {
            throw new BussinesRuleException("Cliente ya registrado");
        }
        if (userRepository.existsByUsername(cmd.getUsername())){
            throw new BussinesRuleException("Cliente ya registrado");
        }
        
        User nuevoUser = User.create(cmd.getEmail(), cmd.getName(),cmd.getUsername(), cmd.getPassword());
        var userSaved = userRepository.save(nuevoUser);
        
        return Map.of(
            "token",jwtService.generateToken(JwtSkeleton.of(userSaved.getId(), userSaved.getEmail())),
            "userData", userSaved
        );
    }
    
}
