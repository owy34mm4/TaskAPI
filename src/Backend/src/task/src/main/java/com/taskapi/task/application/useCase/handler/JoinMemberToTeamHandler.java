package com.taskapi.task.application.useCase.handler;

import org.springframework.stereotype.Service;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.in.IJoinMemberToTeamUseCase;
import com.taskapi.task.application.port.out.IRoleRepository;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.port.out.ITeamRoleRepository;
import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.application.useCase.command.JoinMemberToTeamCommand;
import com.taskapi.task.domain.model.TeamRole;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;   

@Service
@RequiredArgsConstructor
public class JoinMemberToTeamHandler implements IJoinMemberToTeamUseCase {

    private final ICurrentUser currentUser;
    private final IUserDataPort userRepository;
    private final ITeamRepository teamRepository;  
    private final IRoleRepository roleRepository;  
    private final ITeamRoleRepository teamRoleRepository;  
    private final IUserXRoleXTeamRepository userXRoleXTeamRepository;


    @Override
    @Transactional
    public void execute(JoinMemberToTeamCommand cmd) {
        Long requesterId = currentUser.getId();

        if(!userRepository.existsById(requesterId)){throw new BussinesRuleException("Usuario Autenticado Invalido");}

        var team = teamRepository.findByCode(cmd.getTeamCode());

        // Evitar duplicados  
        if (userXRoleXTeamRepository.existsByUserIdAndTeamId(requesterId, team.getId())) {  
            throw new BussinesRuleException("El usuario ya pertenece al equipo");  
        }

        // Buscar rol base del sistema  
        Long observerRoleId = roleRepository.findIdByCode("OBSERVER");

        // Buscar o crear el team_role de ese equipo para OBSERVER  
        TeamRole teamRole = teamRoleRepository.findByTeamIdAndRoleId(team.getId(), observerRoleId);

        // Asignar usuario al teamRole  
        userXRoleXTeamRepository.assignUserToTeamRole(requesterId, teamRole.getId());
        
    }
    
}
