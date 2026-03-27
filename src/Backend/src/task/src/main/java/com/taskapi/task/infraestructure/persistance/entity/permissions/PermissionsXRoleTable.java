package com.taskapi.task.infraestructure.persistance.entity.permissions;

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
@Table(name = "permissions_x_role")  
@Builder @Getter  
public class PermissionsXRoleTable {  
  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "permiso_id")  
    private PermissionsTable permiso;  
  
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "rol_id")  
    private RoleTable rol;  
}