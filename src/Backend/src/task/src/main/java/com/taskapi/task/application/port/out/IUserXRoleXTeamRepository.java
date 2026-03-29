package com.taskapi.task.application.port.out;

public interface IUserXRoleXTeamRepository {
    void assignUserToTeamRole(Long userId, Long teamRoleId);
    boolean existsByUserIdAndTeamId(Long userId, Long teamId);  
    boolean userHasRoleInTeam(Long userId, Long teamId, String roleCode);
    
}
