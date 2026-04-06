package com.taskapi.task.infraestructure.persistance.entity.actionControl;

import com.taskapi.task.domain.model.PermissionsXRole;

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
@Table(name = "permissions_x_role")  
@Builder 
@Getter  
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsXRoleTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "permiso_id")  
    private PermissionsTable permiso;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "rol_id")  
    private RoleTable rol;  
    
    public static PermissionsXRoleTable fromDomain(PermissionsXRole model){
        return PermissionsXRoleTable.builder()
            .id(model.getId())
            .permiso(PermissionsTable.fromDomain(model.getPermiso()))
            .rol(RoleTable.fromDomain(model.getRol()))
        .build();
    }

    public PermissionsXRole toDomain(){
        return PermissionsXRole.reconstitute(
            this.getId(), 
            this.getPermiso().toDomain(), 
            this.getRol().toDomain()
            );
    }


}