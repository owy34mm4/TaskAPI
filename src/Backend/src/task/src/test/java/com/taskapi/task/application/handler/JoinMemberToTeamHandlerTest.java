package com.taskapi.task.application.handler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
// import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.out.IRoleRepository;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.port.out.ITeamRoleRepository;
import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.application.useCase.command.JoinMemberToTeamCommand;
import com.taskapi.task.application.useCase.handler.JoinMemberToTeamHandler;
import com.taskapi.task.domain.model.Role;
import com.taskapi.task.domain.model.Team;
import com.taskapi.task.domain.model.TeamRole;

@ExtendWith(MockitoExtension.class)
public class JoinMemberToTeamHandlerTest {
    
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
    private JoinMemberToTeamHandler handler;

    private JoinMemberToTeamCommand validCommand;
    private Team validTeam;
    // private UserExternalDTO validUser;
    private TeamRole observerRole;

    @BeforeEach
    void setUp(){
        validCommand = JoinMemberToTeamCommand.of(String.valueOf(UUID.randomUUID()));

        // validUser = UserExternalDTO.builder().id(1L).build();

        validTeam = Team.reconstitute(1L, LocalDateTime.now(), "validNameTest", 
            1L, validCommand.getTeamCode(), true);
        
        observerRole = TeamRole.builder().id(2L).role(Role.builder().code("OBSERVER").build()).build();
    }

// Test 1 : HappyPath --- Should Join The memeber into the team
    @Test
    @DisplayName("HappyPath -- Should Join Sucesfully")
    void should_join_sucesfully(){
        when(currentUser.getId()).thenReturn(1L);
        when(userRepository.existsById(1L)).thenReturn(true);

        when(teamRepository.findByCode(validCommand.getTeamCode())).thenReturn(validTeam);

        when(userXRoleXTeamRepository.existsByUserIdAndTeamId(1L,validTeam.getId())).thenReturn(false);

        when(roleRepository.findIdByCode("OBSERVER")).thenReturn(2L);

        when(teamRoleRepository.findByTeamIdAndRoleId(validTeam.getId(), 2L)).thenReturn(observerRole);

        assertDoesNotThrow(()-> handler.execute(validCommand));

        verify(currentUser,times(1)).getId();
        verify(userRepository,times(1)).existsById(1L);

        verify(userXRoleXTeamRepository,times(1)).assignUserToTeamRole(anyLong(), any());


    }

// Test 2 : SadPath --- RequesterDoesntExists
    @Test
    @DisplayName("SadPath --- Should Thrown When Requester Doesnt Exists")
    void should_thrown_when_requester_doesnt_exists(){
        when(currentUser.getId()).thenReturn(900L);
        when(userRepository.existsById(900L)).thenReturn(false);

        assertThrows(BussinesRuleException.class, ()-> handler.execute(validCommand));

        verify(currentUser,times(1)).getId();
        verify(userRepository,times(1)).existsById(900L);

        verify(userXRoleXTeamRepository,never()).assignUserToTeamRole(anyLong(), any());

    }

// Test 3 : EdgeCase ---  ShouldThrown When User Already is in the Team
    @Test
    @DisplayName("EdgeCase -- Should Thrown when user alreadys a Team Member")
    void should_thrown_when_user_Already_in_team(){
        when(currentUser.getId()).thenReturn(1L);
        when(userRepository.existsById(1L)).thenReturn(true);

        when(teamRepository.findByCode(validCommand.getTeamCode())).thenReturn(validTeam);

        when(userXRoleXTeamRepository.existsByUserIdAndTeamId(1L,validTeam.getId())).thenReturn(true);

        assertThrows(BussinesRuleException.class, ()-> handler.execute(validCommand));

        verify(currentUser, times(1)).getId();

        verify(userRepository,times(1)).existsById(1L);

        verify(teamRepository,times(1)).findByCode(validCommand.getTeamCode());

        verify(userXRoleXTeamRepository,never()).assignUserToTeamRole(anyLong(), any());
    }

}
