package com.taskapi.task.infraestructure.persistance.repository.internal.adapter;

import org.springframework.stereotype.Repository;

import com.taskapi.shared.domain.exceptions.NotFoundException;
import com.taskapi.task.application.port.out.IRoleRepository;
import com.taskapi.task.infraestructure.persistance.repository.internal.gateway.IJpaRoleRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements IRoleRepository {

    private final IJpaRoleRepository jpa;


    @Override
    public Long findIdByCode(String code) {
        return jpa.findByCode(code)
            .orElseThrow(()-> new NotFoundException("Role By Code"))
            .getId();
        
    }
    
}
