package com.taskapi.task.application.port.out;


import com.taskapi.task.domain.model.TeamRole;

public interface ITeamRoleRepository {
    Long saveTeamRole(Long teamId, Long roleId);

    TeamRole findByTeamIdAndRoleId(Long teamId, Long roleId);
}
