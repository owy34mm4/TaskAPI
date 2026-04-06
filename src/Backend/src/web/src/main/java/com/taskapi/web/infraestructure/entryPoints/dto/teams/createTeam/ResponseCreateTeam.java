package com.taskapi.web.infraestructure.entryPoints.dto.teams.createTeam;

import com.taskapi.task.domain.model.Team;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ResponseCreateTeam {
    private Long id;

    private String name;

    private String identificationCode;

    private boolean active;

    public static ResponseCreateTeam createFromModel(Team model){
        return ResponseCreateTeam.builder()
            .id(model.getId())
            .name(model.getName())
            .identificationCode(model.getCodigo_identificacion().getValue())
            .active(model.isActive())
        .build();
    }
}
