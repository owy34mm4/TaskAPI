package com.taskapi.task.infraestructure.persistance.repository.internal.adapter;

import org.springframework.stereotype.Repository;

import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.infraestructure.persistance.entity.UserXRoleXTeamTable;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;
import com.taskapi.task.infraestructure.persistance.repository.internal.gateway.IJpaUserXRoleXTeamRepositor;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserXRoleXTeamRepositoryAdapter implements IUserXRoleXTeamRepository {
    private final IJpaUserXRoleXTeamRepositor jpa;

    @Override
    public void assignUserToTeamRole(Long userId, Long teamRoleId) {
        var entity = UserXRoleXTeamTable.builder()
            .usuarioId(userId)
            .teamRole(TeamRoleTable.builder().id(teamRoleId).build()
        ).build();

        jpa.save(entity);
    }
    
}
