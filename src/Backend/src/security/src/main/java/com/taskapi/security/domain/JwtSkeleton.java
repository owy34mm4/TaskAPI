package com.taskapi.security.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtSkeleton {

    private Long id;

    private String email;
    
    public static JwtSkeleton of(Long id, String email){
        return JwtSkeleton.builder()
            .id(id)
            .email(email)
        .build();


    }
}
