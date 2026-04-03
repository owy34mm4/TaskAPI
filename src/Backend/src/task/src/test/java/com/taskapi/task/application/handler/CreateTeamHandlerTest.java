package com.taskapi.task.application.handler;

import static org.junit.jupiter.api.Assertions.assertAll;
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

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.out.IRoleRepository;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.port.out.ITeamRoleRepository;
import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.application.useCase.command.CreateTeamCommand;
import com.taskapi.task.application.useCase.handler.CreateTeamHandler;
import com.taskapi.task.domain.model.Team;

@ExtendWith(MockitoExtension.class)
public class CreateTeamHandlerTest {
    @Mock
    private ICurrentUser currentUser;
    @Mock
    private IUserDataPort userRepository;
    @Mock
    private ITeamRepository teamRepository; 
    @Mock
    private IRoleRepository roleRepository;
    @Mock
    private ITeamRoleRepository teamRoleRepository;
    @Mock
    private IUserXRoleXTeamRepository userXRoleXTeamRepository;

    @InjectMocks
    private CreateTeamHandler hanlder;

    private CreateTeamCommand validCommand;
    
    @BeforeEach
    void setUp(){
        validCommand = CreateTeamCommand.builder()
            .id(null)
            .name("TestName")
            .creacion(LocalDateTime.now())
            .ownerId(1L)
            .identificationCode(null)
            .active(true)
        .build();
    }

// -- Test 1 : HappyPath ---- Should create Sucessfully
    @Test
    @DisplayName("HappyPath --- Should Create Team when properties valid")
    void should_create_sucesfully(){
        when(currentUser.getId()).thenReturn(1L);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(teamRepository.save(any(Team.class)))
            .thenAnswer(invocation -> {
                Team team = invocation.getArgument(0);
                return Team.reconstitute(  
                100L,   
                team.getCreacion(),   
                team.getName(),   
                team.getOwner().getOwnerId(),   
                team.getCodigo_identificacion().getValue(),   
                team.isActive()  
        );
            });
        when(roleRepository.findIdByCode(anyString())).thenReturn(1L);
        when(teamRoleRepository.saveTeamRole(anyLong(), anyLong()))
            .thenAnswer(invocation -> invocation.getArgument(0));
        
        var responseModel = assertDoesNotThrow(()-> hanlder.execute(validCommand));
        
        verify(currentUser,times(1)).getId();
        verify(userRepository,times(1)).existsById(anyLong());
        verify(teamRepository,times(1)).save(any(Team.class));
        verify(userXRoleXTeamRepository,times(1)).assignUserToTeamRole(anyLong(),any());

        assertAll(
            ()->{
                assertNotNull(responseModel);
            }
        );
    }

// -- Test 2 : SadPath --- Requester Doesnt Exists
    @Test
    @DisplayName("SadPath --- Should Thrown When Requester Doesnt Exists")
    void should_thrown_when_requester_doesnt_exists(){
        when(currentUser.getId()).thenReturn(2L);
        when(userRepository.existsById(2L)).thenReturn(false);

        assertThrows(BussinesRuleException.class, ()-> hanlder.execute(validCommand));

        verify(currentUser,times(1)).getId();
        verify(userRepository,times(1)).existsById(2L);

        verify(teamRepository,never()).save(any(Team.class));
        verify(teamRoleRepository,never()).saveTeamRole(anyLong(), any());
        verify(userXRoleXTeamRepository,never()).assignUserToTeamRole(anyLong(), any());

    }
}
