package com.taskapi.task.infraestructure.persistance.repository.internal.adapter;

import org.springframework.stereotype.Repository;

import com.taskapi.shared.domain.exceptions.NotFoundException;
import com.taskapi.task.application.port.out.ITeamRoleRepository;
import com.taskapi.task.domain.model.TeamRole;
import com.taskapi.task.infraestructure.persistance.entity.TeamTable;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.RoleTable;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;
import com.taskapi.task.infraestructure.persistance.repository.internal.gateway.IJpaTeamRoleRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRoleRepositoryAdapter implements ITeamRoleRepository {

    private final IJpaTeamRoleRepository repository;
    private final EntityManager entityManager;


    @Override
    public Long saveTeamRole(Long teamId, Long roleId) {
        var teamRef= entityManager.getReference(TeamTable.class, teamId);
        var roleRef= entityManager.getReference(RoleTable.class, roleId);

        var entity = TeamRoleTable.builder()
            .team(teamRef)
            .role(roleRef)
        .build();

        return repository.save(entity).getId();
    }

    @Override
    public TeamRole findByTeamIdAndRoleId(Long teamId, Long roleId) {
        return repository.findByTeam_IdAndRole_Id(teamId, roleId)
            .orElseThrow(
                ()-> new NotFoundException("TeamRole ")
            ).toDomain();
    }
    
}
