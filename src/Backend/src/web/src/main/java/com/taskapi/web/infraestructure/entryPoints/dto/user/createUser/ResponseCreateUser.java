package com.taskapi.web.infraestructure.entryPoints.dto.user.createUser;



import com.taskapi.user.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ResponseCreateUser {
    Object token;
    boolean active;
    Long id;
    String name;

    public static ResponseCreateUser createFromData(Object domainModel, Object token){
        return ResponseCreateUser.builder()
        .token(token)
        .active(((User) domainModel).isActive())
        .id(((User) domainModel).getId())
        .name(((User) domainModel).getName())
        .build();
    }
}
