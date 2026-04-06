package com.taskapi.task.application.useCase.command;

import lombok.Getter;

@Getter
public class JoinMemberToTeamCommand {
    String teamCode;

    private JoinMemberToTeamCommand(String teamCode){
        this.teamCode= teamCode;
    }

    public static JoinMemberToTeamCommand of(String teamCode){
        return new JoinMemberToTeamCommand(teamCode);

    }
}
