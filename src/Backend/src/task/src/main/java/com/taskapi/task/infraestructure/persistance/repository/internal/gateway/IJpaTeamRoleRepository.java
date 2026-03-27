package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.task.domain.model.TeamRole;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;

public interface IJpaTeamRoleRepository extends JpaRepository<TeamRoleTable, Long> {
    TeamRole save(TeamRole entity);
    
}
