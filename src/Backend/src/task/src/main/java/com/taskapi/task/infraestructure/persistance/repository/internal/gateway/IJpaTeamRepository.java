package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.taskapi.task.infraestructure.persistance.entity.TeamTable;

public interface IJpaTeamRepository extends JpaRepository<TeamTable,Long>{  
    
    @Query("SELECT t FROM TeamTable t WHERE t.codigoIdentificacion = :codigo")
    Optional<TeamTable> findByCode(@Param("codigo")String codigo);
}
