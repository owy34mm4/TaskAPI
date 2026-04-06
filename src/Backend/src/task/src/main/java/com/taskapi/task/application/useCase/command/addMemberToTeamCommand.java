package com.taskapi.task.application.useCase.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class addMemberToTeamCommand {
    String teamCode;

    public static addMemberToTeamCommand of(String teamCode){
        return addMemberToTeamCommand.builder()
            .teamCode(teamCode)
        .build();
    }
}
