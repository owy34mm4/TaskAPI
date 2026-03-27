package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.task.infraestructure.persistance.entity.UserXRoleXTeamTable;

public interface IJpaUserXRoleXTeamRepositor extends JpaRepository<UserXRoleXTeamTable, Long>{

    UserXRoleXTeamTable save(UserXRoleXTeamTable entity);
    
}
