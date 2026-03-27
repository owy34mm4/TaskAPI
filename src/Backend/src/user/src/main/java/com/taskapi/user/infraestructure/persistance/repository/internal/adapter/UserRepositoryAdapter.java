package com.taskapi.user.infraestructure.persistance.repository.internal.adapter;


import org.springframework.stereotype.Repository;

import com.taskapi.shared.domain.exceptions.NotFoundException;
import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.domain.model.User;
import com.taskapi.user.infraestructure.persistance.entity.UserTable;
import com.taskapi.user.infraestructure.persistance.repository.internal.gateway.IJPAUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements IUserRepository {

    private final IJPAUserRepository repository;

    @Override
    public User findById(Long id) {
        return repository.findById(id)
            .orElseThrow(()-> new NotFoundException("User")).toDomain();
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(()-> new NotFoundException("User")).toDomain();
    }

    @Override
    public User save(User user) {
        return repository.save(
            UserTable.fromDomain(user)
        ).toDomain();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsernameOrEmail(String value) {
        return repository.existsByUsernameOrEmail(value);
    }

    @Override
    public User findByUsernameOrEmail(String value) {
        return repository.findByEmailOrUsername(value)
            .orElseThrow(()-> new NotFoundException("User")).toDomain();
    }

    @Override
    public boolean existsByUsername(String username) {
        return  repository.existsByUsername(username);
    }
    
}
