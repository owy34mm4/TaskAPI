package com.taskapi.user.infraestructure.persistance.repository.internal.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskapi.user.infraestructure.persistance.entity.UserTable;

@Repository
public interface IJPAUserRepository extends JpaRepository<UserTable, Long>{

    boolean existsByEmail(String email);

    Optional<UserTable> findByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM UserTable u WHERE u.email = :value OR u.username = :value")  
    boolean existsByUsernameOrEmail(@Param("value") String value);

    @Query("SELECT u FROM UserTable u WHERE u.email = :value OR u.username = :value")  
    Optional<UserTable> findByEmailOrUsername(@Param("value") String value);
}
