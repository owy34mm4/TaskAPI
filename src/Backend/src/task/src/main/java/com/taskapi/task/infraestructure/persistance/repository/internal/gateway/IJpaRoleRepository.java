package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.task.infraestructure.persistance.entity.actionControl.RoleTable;

public interface IJpaRoleRepository extends JpaRepository<RoleTable, Long>{
    Optional<RoleTable> findByCode(String code);

}