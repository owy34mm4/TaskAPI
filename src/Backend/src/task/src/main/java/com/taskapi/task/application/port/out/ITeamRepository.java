package com.taskapi.task.application.port.out;

import java.util.List;

import com.taskapi.task.domain.model.Team;

public interface ITeamRepository {
    Team save (Team entity);
    Team findByCode(String teamCode);
    List<Team> findAllByUserId(Long userId);
}
