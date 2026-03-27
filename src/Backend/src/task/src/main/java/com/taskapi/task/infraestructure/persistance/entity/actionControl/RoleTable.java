package com.taskapi.task.infraestructure.persistance.entity.actionControl;

import java.util.List;

import com.taskapi.task.domain.model.Role;
import com.taskapi.task.domain.model.enums.ERoleScope;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "system_roles")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleTable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false)  
    private String nombre;  

    @Column(nullable = false, unique = true)  
    private String code;
  
    private String descripcion;  

    @Enumerated(EnumType.STRING)  
    @Column(nullable = false)  
    private ERoleScope scope;    // ← SYSTEM o TEAM
  
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)  
    private List<PermissionsXRoleTable> permisos;

    public static RoleTable fromDomain(Role model){
        return RoleTable.builder()
            .id(model.getId())
            .nombre(model.getNombre())
            .code(model.getCode())
            .descripcion(model.getDescripcion())
            .scope(ERoleScope.valueOf(model.getScope().name()))
            .permisos(null)
        .build();
    }

    public Role toDomain(){
        return Role.reconstitute(
            this.getId(), 
            this.getNombre(), 
            this.getCode(), 
            this.getDescripcion(), 
            List.of()
            // this.getPermisos()==null ? List.of() : this.getPermisos().stream().map(i -> i.toDomain()).toList()
        );
    }
}
