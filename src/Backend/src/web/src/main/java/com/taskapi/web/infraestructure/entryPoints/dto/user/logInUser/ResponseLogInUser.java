package com.taskapi.web.infraestructure.entryPoints.dto.user.logInUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class ResponseLogInUser {
    Object token;

    public static ResponseLogInUser createFromData(Object token){
        return ResponseLogInUser.builder()
            .token(token)
        .build();
    }
}
