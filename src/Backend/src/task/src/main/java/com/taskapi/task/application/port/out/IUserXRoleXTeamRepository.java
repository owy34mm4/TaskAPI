package com.taskapi.task.application.port.out;

public interface IUserXRoleXTeamRepository {
    void assignUserToTeamRole(Long userId, Long teamRoleId);
}
