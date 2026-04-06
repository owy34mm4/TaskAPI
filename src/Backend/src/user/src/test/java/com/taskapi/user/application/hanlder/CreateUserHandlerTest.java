package com.taskapi.user.application.hanlder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.taskapi.shared.application.port.out.security.IJwtConsumer;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.application.useCase.command.CreateUserCommand;
import com.taskapi.user.application.useCase.handler.CreateUserHandler;
import com.taskapi.user.domain.model.User;

@ExtendWith(MockitoExtension.class)
public class CreateUserHandlerTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IJwtConsumer jwtService;

    @InjectMocks
    private CreateUserHandler handler;

    // Data reutilizable
    private CreateUserCommand validCommand;  
    
    @BeforeEach
    void setUp(){
        validCommand = CreateUserCommand.builder()
            .id(1L)
            .name("test")
            .username("testingname")
            .email("test@test.gmail.com")
            .password("testPwordLargaComoDebeSer")
            .active(true)
        .build();
  
    }

//--Test 1: HappyPath ------
    @Test
    @DisplayName("HappyPath ---- Should create User When properties valid")
    void should_create_user_sucesfully(){
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class)))
            .thenAnswer(invocation-> invocation.getArgument(0));
        when(jwtService.generateToken(any())).thenReturn("mocked-JWT");

        var result = assertDoesNotThrow(()->handler.execute(validCommand));

        verify(userRepository,times(1)).existsByEmail(anyString());
        verify(userRepository,times(1)).save(any(User.class));

        verify(jwtService,times(1)).generateToken(any());

        assertAll(
            ()->{
                assertNotNull(result);
            }
        );

    }

//--Test2 : Error cuando email ya está registrado
    @Test
    @DisplayName("Should Thrown When Email Already Linked")
    void should_thrown_when_email_already_linked(){
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BussinesRuleException.class,()->handler.execute(validCommand));

        verify(userRepository,times(1)).existsByEmail(anyString());

        verify(userRepository,never()).save(any());

        verify(jwtService,never()).generateToken(any());

    }

    
}
