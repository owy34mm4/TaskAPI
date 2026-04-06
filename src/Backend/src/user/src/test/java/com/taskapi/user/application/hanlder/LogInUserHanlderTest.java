package com.taskapi.user.application.hanlder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.taskapi.shared.application.port.out.security.IJwtConsumer;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.application.useCase.command.LogInUserCommand;
import com.taskapi.user.application.useCase.handler.LogInUserHandler;
import com.taskapi.user.domain.model.User;

@ExtendWith(MockitoExtension.class)
public class LogInUserHanlderTest {

    @Mock
    private IUserRepository userRepository;
    
    @Mock
    private IJwtConsumer jwtService;

    @InjectMocks 
    LogInUserHandler handler;

    // Data reutilizable
    private LogInUserCommand validCommand;  
    private User validUser;  
    
    @BeforeEach
    void setUp(){
        validCommand = LogInUserCommand.builder()
            .usernameOrEmail("test@test.com")
            .password("plainTestLongEnough")
        .build();

        String hashedPassword = new BCryptPasswordEncoder()  
        .encode("plainTestLongEnough");
  
       validUser= User.reconstitute(
            1L,
            "test", 
            "test@test.com",
            "testusername",
            hashedPassword
        );
    }


// ---- Test1: Happy Path ------
    @Test
    @DisplayName("HappyPath ---- Should Login Sucesfully")
    void should_login_sucesfully(){
        when(userRepository.existsByUsernameOrEmail(anyString())).thenReturn(true);
        when(userRepository.findByUsernameOrEmail(anyString())).thenReturn(validUser);
        when(jwtService.generateToken(any())).thenReturn("mocked-JWT");

        var result = assertDoesNotThrow(()->handler.execute(validCommand));

        assertNotNull(result);

        verify(userRepository,times(1)).existsByUsernameOrEmail(anyString());
        verify(userRepository,times(1)).findByUsernameOrEmail(anyString());

        verify(jwtService,times(1)).generateToken(any());

    }

// ----- Test2 --- Should Thrown when User Not Registered
    @Test
    @DisplayName("Should Thrown when user is not registered")
    void should_thrown_when_user_is_not_registered(){
        when(userRepository.existsByUsernameOrEmail(anyString())).thenReturn(false);

        assertThrows(BussinesRuleException.class,()->handler.execute(validCommand));

        verify(userRepository,times(1)).existsByUsernameOrEmail(anyString());
        verify(userRepository,never()).findByUsernameOrEmail(anyString());
        verify(jwtService,never()).generateToken(any());

    }
}
