package com.taskapi.task.application.handler;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.application.useCase.command.GetAllTeamsByUserIdCommand;
import com.taskapi.task.application.useCase.handler.GetAllTeamsByUserIdHandler;
import com.taskapi.task.domain.model.Team;

@ExtendWith(MockitoExtension.class)
public class GetAllTeamsByUserIdHandlerTest {
    @Mock private ICurrentUser currentUser;  
    @Mock private IUserDataPort userRepository;  
    @Mock private ITeamRepository teamRepository;  
    @Mock private IUserXRoleXTeamRepository relationalRespository; 
      
    @InjectMocks private GetAllTeamsByUserIdHandler handler;  
  
    private final Long REQUESTER_ID = 1L;  
    private final Long TEAM_ID = 100L;  
    private final Long MEMBER_ID = 2L;  
    
    private GetAllTeamsByUserIdCommand validCommand;


    // Helpers para construir objetos de prueba  
    private Team buildTeam(Long teamId, Long ownerId) {  
        return Team.reconstitute(teamId, LocalDateTime.now(), "Team Test", ownerId,   
            UUID.randomUUID().toString(), true);  
    }  

    private UserExternalDTO buildUserDTO(Long id) {  
        return UserExternalDTO.builder()
            .id(id)
            .name("User " + id)
            .username("user" + id)
            .email("user" + id + "@test.com")
            .password("user" + id)
        .build();
        
    }

    @BeforeEach
    void setUp(){
        validCommand = GetAllTeamsByUserIdCommand.of("1");

    }

// Test 1 : HappyPath ----Should return teams with owner and members assembled
    @Test  
    @DisplayName("HappyPath --- Should return teams with owner and members assembled")  
    void should_return_teams_with_owner_and_members() {  
        Team team = buildTeam(TEAM_ID, REQUESTER_ID);  
        UserExternalDTO ownerDTO = buildUserDTO(REQUESTER_ID);  
        UserExternalDTO memberDTO = buildUserDTO(MEMBER_ID);  
    
        // Simular el Object[] que retorna la query JPQL  
        List<Object[]> rows = new ArrayList<>();  
        rows.add(new Object[]{TEAM_ID, MEMBER_ID});
    
        when(currentUser.getId()).thenReturn(REQUESTER_ID);  
        when(userRepository.existsById(REQUESTER_ID)).thenReturn(true);  
        when(teamRepository.findAllByUserId(REQUESTER_ID)).thenReturn(List.of(team));  
        when(userRepository.findById(REQUESTER_ID)).thenReturn(ownerDTO);  
        when(relationalRespository.findMemberIdsByTeamIds(List.of(TEAM_ID)))  
            .thenReturn(rows);  
        when(userRepository.findAllById(List.of(MEMBER_ID)))  
            .thenReturn(List.of(memberDTO));  
    
        var result = handler.execute(validCommand);  
    
        assertAll(  
            () -> assertNotNull(result),  
            () -> assertEquals(1, result.size()),  
            () -> assertNotNull(result.get(0).getOwner().getOwnerData()),  
            () -> assertEquals(1, result.get(0).getMembers().size()),  
            () -> assertEquals(MEMBER_ID, result.get(0).getMembers().get(0).getMemberId())  
        );  
    }

// Test 2 : SadPath --- ShouldThrownWhenRequesterDoesNotExist
    @Test  
    @DisplayName("SadPath --- Should throw when requester does not exist")  
    void should_throw_when_user_invalid() {  
        when(currentUser.getId()).thenReturn(REQUESTER_ID);  
        when(userRepository.existsById(REQUESTER_ID)).thenReturn(false);  
    
        assertThrows(BussinesRuleException.class,  
            () -> handler.execute(validCommand));  
    }

// Test 3 : EdgeCase ---- User With No Teams

    @Test
    @DisplayName("EdgeCase ---should return Empty when no teams ")
    void should_return_Empty_when_no_teams(){
        when(currentUser.getId()).thenReturn(REQUESTER_ID);  
        when(userRepository.existsById(REQUESTER_ID)).thenReturn(true);  
        when(teamRepository.findAllByUserId(REQUESTER_ID)).thenReturn(List.of());  
        when(relationalRespository.findMemberIdsByTeamIds(List.of())).thenReturn(List.of());  
        when(userRepository.findAllById(List.of())).thenReturn(List.of());  
    
        var result = handler.execute(validCommand);  
    
        assertNotNull(result);  
        assertTrue(result.isEmpty());
    }

}
