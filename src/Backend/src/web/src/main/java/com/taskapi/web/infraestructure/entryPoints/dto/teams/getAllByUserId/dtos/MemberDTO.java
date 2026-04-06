package com.taskapi.web.infraestructure.entryPoints.dto.teams.getAllByUserId.dtos;

import com.taskapi.task.domain.valueObject.TeamMember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    private String name;
    private String email;
    private String username;

    public static MemberDTO dumpFromExternal(TeamMember entity){
        return MemberDTO.builder()
            .name(entity.getMember().getName())
            .email(entity.getMember().getEmail())
            .username(entity.getMember().getUsername())
        .build();
    }
}
