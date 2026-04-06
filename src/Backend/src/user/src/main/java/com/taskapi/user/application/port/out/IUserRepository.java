package com.taskapi.user.application.port.out;

import java.util.List;

import com.taskapi.user.domain.model.User;

public interface IUserRepository {
    User findById(Long id);
    List<User> findAllById(List<Long> ids);
    User findByEmail(String email);
    User save(User user);

    boolean existsById(Long id);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByUsernameOrEmail(String value);

    User findByUsernameOrEmail(String value);
    
}
