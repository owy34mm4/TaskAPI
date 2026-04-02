package com.taskapi.task.infraestructure.persistance.repository.internal.gateway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskapi.task.infraestructure.persistance.entity.UserXRoleXTeamTable;

public interface IJpaUserXRoleXTeamRepository extends JpaRepository<UserXRoleXTeamTable, Long>{

    UserXRoleXTeamTable save(UserXRoleXTeamTable entity);

    boolean existsByUsuarioIdAndTeamRole_Team_Id (Long userId, Long teamId);

    boolean existsByUsuarioIdAndTeamRole_Team_IdAndTeamRole_Role_Code(Long userId, Long teamId, String roleCode);

    @Query("""  
    SELECT ux.teamRole.team.id, ux.usuarioId   
    FROM UserXRoleXTeamTable ux  
    WHERE ux.teamRole.team.id IN :teamIds  
    """) 
    List<Object[]> findMemberIdsByTeamIds(@Param("teamIds") List<Long> teamIds);
    
}
