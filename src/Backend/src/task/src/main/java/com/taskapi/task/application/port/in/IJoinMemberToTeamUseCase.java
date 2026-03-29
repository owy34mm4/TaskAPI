package com.taskapi.task.application.port.in;

import com.taskapi.task.application.useCase.command.JoinMemberToTeamCommand;

public interface IJoinMemberToTeamUseCase {
    void execute (JoinMemberToTeamCommand cmd);

}
