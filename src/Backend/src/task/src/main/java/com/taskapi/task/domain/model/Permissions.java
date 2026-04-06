package com.taskapi.task.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Permissions {
    private Long id;  
 
    private String code;       
  
    private String nombre;  
  
    private String descripcion;  

    public static Permissions reconstitute(Long id, String code, String nombre, String descripcion){
        return Permissions.builder()
            .id(id)
            .code(code)
            .nombre(nombre)
            .descripcion(descripcion)
        .build();

    }
}
