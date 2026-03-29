package com.taskapi.task.infraestructure.persistance.repository.internal.adapter;

import org.springframework.stereotype.Repository;

import com.taskapi.shared.domain.exceptions.NotFoundException;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.domain.model.Team;
import com.taskapi.task.infraestructure.persistance.entity.TeamTable;
import com.taskapi.task.infraestructure.persistance.repository.internal.gateway.IJpaTeamRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryAdapter implements ITeamRepository {

    private final IJpaTeamRepository repository;


    @Override
    public Team save(Team entity) {
        return repository.save(TeamTable.fromDomain(entity)).toDomain();
    }


    @Override
    public Team findByCode(String teamCode) {
        return repository.findByCode(teamCode).orElseThrow(()-> new NotFoundException("Team by Code")).toDomain();
    }
    
}
