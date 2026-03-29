package com.taskapi.task.application.useCase.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.in.ICreateTeamUseCase;
import com.taskapi.task.application.port.out.IRoleRepository;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.port.out.ITeamRoleRepository;
import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.application.useCase.command.CreateTeamCommand;
import com.taskapi.task.domain.model.Team;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTeamHandler implements ICreateTeamUseCase {

    private final ICurrentUser currentUser;

    private final IUserDataPort userRepository;
    private final ITeamRepository teamRepository; 
    private final IRoleRepository roleRepository;
    private final ITeamRoleRepository teamRoleRepository;
    private final IUserXRoleXTeamRepository userXRoleXTeamRepository;

    private final List<String> SYSTEM_ROLES = List.of("OWNER", "OBSERVER");
    private boolean doesNotExists(Long id){
        return !userRepository.existsById(id);
    }

    @Override
    @Transactional
    public Team execute(CreateTeamCommand cmd) {
        var requesterId = currentUser.getId();
        if(doesNotExists(requesterId)){throw new BussinesRuleException("Usuario Invalido");}

        // Creamos la entidad equipo
            var teamCreated = Team.create(cmd.getName(), requesterId);
            teamCreated = teamRepository.save(teamCreated);

        //Generamos los roles de sistema de cada equipo 
        Map<String, Long> teamRoleIds = new HashMap<>();
        for(String roleCode : SYSTEM_ROLES){
            Long roleId = roleRepository.findIdByCode(roleCode);
            Long teamRoleId = teamRoleRepository.saveTeamRole(teamCreated.getId(), roleId);
            teamRoleIds.put(roleCode, teamRoleId);
        }

       
        //Asiganos requester al rol owner
        userXRoleXTeamRepository.assignUserToTeamRole(requesterId, teamRoleIds.get("OWNER"));

        return teamCreated;
    }
    
}
