package com.taskapi.user.infraestructure.persistance.repository.external.adapter;

import org.springframework.stereotype.Repository;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.user.infraestructure.persistance.repository.internal.gateway.IJPAUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExternalUserRepositoryAdapter implements IUserDataPort {

    private final IJPAUserRepository repository;

    @Override
    public UserExternalDTO save(UserExternalDTO data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public UserExternalDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
        
    }
    
}
