package com.taskapi.user.application.port.out;

import com.taskapi.user.domain.model.User;

public interface IUserRepository {
    User findById(Long id);
    User findByEmail(String email);
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmail(String value);

    User findByUsernameOrEmail(String value);
    
}
