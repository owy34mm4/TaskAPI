package com.taskapi.web.infraestructure.entryPoints.dto.teams;

import com.taskapi.task.application.useCase.command.CreateTeamCommand;

import lombok.Getter;

@Getter
public class RequestCreateTeam {
    private String name;
    

    public CreateTeamCommand toCommand(){
        return CreateTeamCommand.builder()
            .id(null)
            .name(name)
            .creacion(null)
            .ownerId(null)
            .identificationCode(null)
            .active(false)
        .build();
        
    }
}
