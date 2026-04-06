package com.taskapi.user.domain.model;

import java.util.List;

import com.taskapi.user.domain.model.valueObjects.HashedPassword;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private Long id;

    private String name;

    private String email;

    private String username;

    private HashedPassword password;

    private boolean active;

    private List<Long> equipos_ids;

    private Object roles;

    private Object tareas;

    //Constructor para construir usuario NUEVO (recibe plainPassword)
    public static User create(String email, String name, String username , String plainPassword){
        return User.builder()
            .id(null)
            .name(name)
            .email(email)
            .username(username)
            .password(HashedPassword.of(plainPassword))
            .active(true)
        .build();
    }

    // Constructor para reconstruir desde DB (recibe hash ya existente)  
    public static User reconstitute(Long id, String name, String email, String username , String existingHash) {  
        return User.builder()
            .id(id)
            .name(name)
            .email(email)
            .username(username)
            .password(HashedPassword.fromHash(existingHash))
            .active(true)
        .build();
    }

    public boolean verifyPassword(String plainPassword){
        return password.matches(plainPassword);
    }
}
