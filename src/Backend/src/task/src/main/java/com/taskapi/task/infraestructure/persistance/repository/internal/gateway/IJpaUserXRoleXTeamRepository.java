package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.task.infraestructure.persistance.entity.UserXRoleXTeamTable;

public interface IJpaUserXRoleXTeamRepository extends JpaRepository<UserXRoleXTeamTable, Long>{

    UserXRoleXTeamTable save(UserXRoleXTeamTable entity);

    boolean existsByUsuarioIdAndTeamRole_Team_Id (Long userId, Long teamId);

    boolean existsByUsuarioIdAndTeamRole_Team_IdAndTeamRole_Role_Code(Long userId, Long teamId, String roleCode);
    
}
