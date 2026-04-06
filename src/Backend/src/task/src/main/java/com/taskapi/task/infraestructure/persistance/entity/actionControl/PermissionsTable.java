package com.taskapi.task.infraestructure.persistance.entity.actionControl;

import com.taskapi.task.domain.model.Permissions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity  
@Table(name = "permissions")  
@Builder
@Getter  

public class PermissionsTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  

    @Column(nullable = false, unique = true)  
    private String code;        // ← "TASK_CREATE", "TEAM_DELETE", etc.
  
    @Column(nullable = false)  
    private String nombre;  
  
    private String descripcion;  

    public static PermissionsTable fromDomain(Permissions model){
        return PermissionsTable.builder()
            .id(model.getId())
            .code(model.getCode())
            .nombre(model.getNombre())
            .descripcion(model.getDescripcion())
        .build();
    }

    public Permissions toDomain(){
        return Permissions.reconstitute(
            this.getId(), 
            this.getCode(), 
            this.getNombre(), 
            this.getDescripcion()
        );
    }
}