package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.task.infraestructure.persistance.entity.TeamTable;

public interface IJpaTeamRepository extends JpaRepository<TeamTable,Long>{   
}
