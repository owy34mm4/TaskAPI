package com.taskapi.user.infraestructure.persistance.repository.external.adapter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.infraestructure.persistance.repository.external.mapper.UserExternalMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExternalUserRepositoryAdapter implements IUserDataPort {

    private final IUserRepository repository;
    private final UserExternalMapper externalMapper;

    @Override
    public UserExternalDTO save(UserExternalDTO data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public UserExternalDTO findById(Long id) {
        return externalMapper.toExternal(repository.findById(id));
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
        
    }

    @Override
    public List<UserExternalDTO> findAllById(List<Long> ids) {
        return repository.findAllById(ids).stream().map(item -> externalMapper.toExternal(item)).toList();
    }

        
}
