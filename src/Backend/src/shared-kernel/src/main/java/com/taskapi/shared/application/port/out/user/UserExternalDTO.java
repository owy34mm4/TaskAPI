package com.taskapi.shared.application.port.out.user;

import java.util.List;

import lombok.Getter;

@Getter
public class UserExternalDTO {

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private boolean active;

    private List<Long> equipos_ids;
}
