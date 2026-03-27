package com.taskapi.task.application.port.out;

import com.taskapi.task.domain.model.Team;

public interface ITeamRepository {
    Team save (Team entity);
}
