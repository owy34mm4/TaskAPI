package com.taskapi.task.application.port.in;

import com.taskapi.task.application.useCase.command.CreateTeamCommand;
import com.taskapi.task.domain.model.Team;

public interface ICreateTeamUseCase {
    Team execute (CreateTeamCommand cmd);
}
