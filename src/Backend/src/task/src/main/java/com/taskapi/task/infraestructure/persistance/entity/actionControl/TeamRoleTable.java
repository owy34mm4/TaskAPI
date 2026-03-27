package com.taskapi.task.infraestructure.persistance.entity.actionControl;

import com.taskapi.task.domain.model.Team;
import com.taskapi.task.domain.model.TeamRole;
import com.taskapi.task.infraestructure.persistance.entity.TeamTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity  
@Table(name = "team_roles")  
@Builder @Getter  
@NoArgsConstructor @AllArgsConstructor  
public class TeamRoleTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    // FK al equipo  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "team_id")
    private TeamTable team;  
  
    // FK al rol del sistema (OWNER, OBSERVADOR, o custom)  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "role_id")  
    private RoleTable role;  
  
    // Nombre personalizado opcional (ej: "Líder Técnico")  
    @Column(name = "nombre_personalizado")  
    private String nombrePersonalizado;  

    
    public TeamRoleTable fromDomain(TeamRole model){
        return TeamRoleTable.builder()
            .id(model.getId())
            .team(TeamTable.fromDomain(model.getTeam()))
            .role(RoleTable.fromDomain(model.getRole()))
            .nombrePersonalizado(model.getNombrePersonalizado())
        .build();
    }

    public TeamRole toDomain(){
        return TeamRole.reconstitute(
            this.getId(), 
            this.getTeam().toDomain(), 
            this.getRole().toDomain(), 
            this.getNombrePersonalizado()
        );
    }
}
