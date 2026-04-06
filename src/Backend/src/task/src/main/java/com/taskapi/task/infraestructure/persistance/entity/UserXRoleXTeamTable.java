package com.taskapi.task.infraestructure.persistance.entity;

import com.taskapi.task.infraestructure.persistance.entity.actionControl.TeamRoleTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity  
@Table(name = "usuario_x_rol_x_equipo")  
@Builder @Getter  
public class UserXRoleXTeamTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    // ← ID externo, módulo user  
    @Column(name = "usuario_id", nullable = false)  
    private Long usuarioId;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "team_role_id")  
    private TeamRoleTable teamRole; // ← apunta al rol dentro del equipo
}
