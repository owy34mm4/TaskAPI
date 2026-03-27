package com.taskapi.task.application.useCase.handler;

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

        //Obtenemos el id del Rol 'OWNER', generado como rol de sistema
            Long ownerRoleId = roleRepository.findIdByCode("OWNER");

        //Creamos el 'teamRole' (vinculamos el equipo con el Rol owner)
            Long TeamRoleId = teamRoleRepository.saveTeamRole(teamCreated.getId(), ownerRoleId);

        //Asiganos requester al rol owner
        userXRoleXTeamRepository.assignUserToTeamRole(requesterId, TeamRoleId);

        return teamCreated;
    }
    
}
