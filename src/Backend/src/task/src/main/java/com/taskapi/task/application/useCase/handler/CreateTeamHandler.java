package com.taskapi.task.application.useCase.handler;

import org.springframework.stereotype.Service;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.in.ICreateTeamUseCase;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.useCase.command.CreateTeamCommand;
import com.taskapi.task.domain.model.Team;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTeamHandler implements ICreateTeamUseCase {

    private final ICurrentUser currentUser;

    private final IUserDataPort userRepository;

    private final ITeamRepository teamRepository; 


    private boolean doesNotExists(Long id){
        return !userRepository.existsById(id);
    }

    @Override
    public Team execute(CreateTeamCommand cmd) {
        var requesterId = currentUser.getId();
        if(doesNotExists(requesterId)){throw new BussinesRuleException("Usuario Invalido");}

        var teamCreated = Team.create(cmd.getName(), requesterId);

        teamCreated = teamRepository.save(teamCreated);

        return teamCreated;
    }
    
}
