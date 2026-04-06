package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.task.domain.model.TeamRole;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;

public interface IJpaTeamRoleRepository extends JpaRepository<TeamRoleTable, Long> {
    TeamRoleTable save(TeamRole entity);

    Optional<TeamRoleTable> findByTeam_IdAndRole_Id(Long teamId, Long RoleId);
    
}
