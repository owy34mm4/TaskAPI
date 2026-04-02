package com.taskapi.web.infraestructure.entryPoints.dto.teams.getAllByUserId.dtos;

import java.util.List;

import com.taskapi.task.domain.model.Team;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamDTO {
    
    private Long id;

    private String name;

    private OwnerDTO owner;

    private List<MemberDTO> members;

    public static TeamDTO dumpFromDomain(Team model){
        return TeamDTO.builder()
            .id(model.getId())
            .name(model.getName())
            .owner(OwnerDTO.dumpFromExternal(model.getOwner().getOwnerData()))
            .members(model.getMembers().stream().map(item -> MemberDTO.dumpFromExternal(item)).toList())
        .build();
    }
}
