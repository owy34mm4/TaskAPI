package com.taskapi.web.infraestructure.entryPoints.dto.teams.getAllByUserId.dtos;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.user.domain.model.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OwnerDTO {
    private String name;
    private String email;
    private String username;

    public static OwnerDTO dumpFromDomain(User domain){
        return OwnerDTO.builder().build();
    }

    public static OwnerDTO dumpFromExternal(UserExternalDTO entity){
        return OwnerDTO.builder()
            .name(entity.getName())
            .email(entity.getEmail())
            .username(entity.getUsername())
        .build();
    }
}
