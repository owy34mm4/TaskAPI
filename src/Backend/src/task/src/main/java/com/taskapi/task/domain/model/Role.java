package com.taskapi.task.domain.model;

import java.util.List;

import com.taskapi.task.domain.model.enums.ERoleScope;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Role {
    private Long id;  
   
    private String nombre;  
   
    private String code;
  
    private String descripcion;  

    private ERoleScope scope;    // ← SYSTEM o TEAM
  
    private List<PermissionsXRole> permisos;

    // public static Role createSystemRole(String nombre, String code, String description, List<Permissions> permisos){
    //     return Role.builder()
    //         .id(null)
    //         .nombre(nombre)
    //         .code(code)
    //         .descripcion(description)
    //         .scope(ERoleScope.SYSTEM)
    //         .permisos(permisos)
    //     .build();
    // }

    public static Role createTeamRole(String nombre, String code, String description, List<PermissionsXRole> permisos){
        return Role.builder()
            .id(null)
            .nombre(nombre)
            .code(code)
            .descripcion(description)
            .scope(ERoleScope.TEAM)
            .permisos(permisos)
        .build();
    }

    public static Role reconstitute(Long id, String nombre, String code, String description, List<PermissionsXRole> permisos){
        return Role.builder()
            .id(id)
            .nombre(nombre)
            .code(code)
            .descripcion(description)
            .scope(ERoleScope.TEAM)
            .permisos(permisos)
        .build();
    }


}
