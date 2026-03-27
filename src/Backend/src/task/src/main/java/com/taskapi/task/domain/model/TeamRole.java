package com.taskapi.task.domain.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamRole {
    private Long id;  
  
    private Team team;  
    
    private Role role;  
    
    private String nombrePersonalizado;  

    public static TeamRole reconstitute(Long id, Team team, Role role, String nombrePersonalizado){
        return TeamRole.builder()
            .id(id)
            .team(team)
            .role(role)
            .nombrePersonalizado(nombrePersonalizado)
        .build();
    }

    
}
