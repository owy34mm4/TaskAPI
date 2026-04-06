package com.taskapi.shared.application.port.out.user;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserExternalDTO {

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private boolean active;

}
