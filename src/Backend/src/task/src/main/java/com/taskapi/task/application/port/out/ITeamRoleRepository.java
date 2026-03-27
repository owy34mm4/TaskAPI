package com.taskapi.task.application.port.out;

public interface ITeamRoleRepository {
    Long saveTeamRole(Long teamId, Long roleId);
}
