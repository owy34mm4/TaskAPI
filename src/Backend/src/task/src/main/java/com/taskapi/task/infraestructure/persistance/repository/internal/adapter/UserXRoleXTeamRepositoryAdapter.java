package com.taskapi.task.infraestructure.persistance.repository.internal.adapter;

import org.springframework.stereotype.Repository;

import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.infraestructure.persistance.entity.UserXRoleXTeamTable;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;
import com.taskapi.task.infraestructure.persistance.repository.internal.gateway.IJpaUserXRoleXTeamRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserXRoleXTeamRepositoryAdapter implements IUserXRoleXTeamRepository {
    private final IJpaUserXRoleXTeamRepository jpa;

    @Override
    public void assignUserToTeamRole(Long userId, Long teamRoleId) {
        var entity = UserXRoleXTeamTable.builder()
            .usuarioId(userId)
            .teamRole(TeamRoleTable.builder().id(teamRoleId).build()
        ).build();

        jpa.save(entity);
    }

    @Override
    public boolean existsByUserIdAndTeamId(Long userId, Long teamId) {
        return jpa.existsByUsuarioIdAndTeamRole_Team_Id(userId, teamId);
    }

    @Override
    public boolean userHasRoleInTeam(Long userId, Long teamId, String roleCode) {
        return jpa.existsByUsuarioIdAndTeamRole_Team_IdAndTeamRole_Role_Code(userId, teamId, roleCode);
    }
    
}
