package com.taskapi.task.application.port.in;

import java.util.List;

import com.taskapi.task.application.useCase.command.GetAllTeamsByUserIdCommand;
import com.taskapi.task.domain.model.Team;

public interface IGetAllTeamsByUserId {
    List<Team> execute(GetAllTeamsByUserIdCommand cmd);
}
