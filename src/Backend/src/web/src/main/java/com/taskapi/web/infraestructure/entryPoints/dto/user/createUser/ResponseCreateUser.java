package com.taskapi.web.infraestructure.entryPoints.dto.user.createUser;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ResponseCreateUser {
    Object token;

    public static ResponseCreateUser createFromData(Object domainModel, Object token){
        return ResponseCreateUser.builder()
        .token(token)
        .build();
    }
}
