package com.taskapi.task.infraestructure.persistance.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.taskapi.task.domain.model.Team;
import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity  
@Table(name = "teams")  
@Builder @Getter  
public class TeamTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false)  
    private String nombre;  
  
    private LocalDateTime creacion;  
  
    // ← referencia por ID, no por entidad (módulo user separado)  
    @Column(name = "dueno_id", nullable = false)  
    private Long duenoId;  
  
    private String codigoIdentificacion;  
  
    private boolean activo;  
  
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)  
    private List<TeamRoleTable> teamRoles; 

    public static TeamTable fromDomain(Team team) {  
        return TeamTable.builder()  
            .id(team.getId())  
            .nombre(team.getName())  
            .creacion(team.getCreacion())  
            .duenoId(team.getOwner_id())  
            .codigoIdentificacion(team.getCodigo_identificacion().getValue())  
            .activo(team.isActive())  
            .build();  
    }  
   
    public Team toDomain() {  
        return Team.reconstitute(  
            this.id,  
            this.creacion,  
            this.nombre,  
            this.duenoId,  
            this.codigoIdentificacion,  
            this.activo  
        );  
    }
}