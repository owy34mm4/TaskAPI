package com.taskapi.task.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionsXRole {
    
    private Long id;  
  
    private Permissions permiso;  
  
    private Role rol;  

    public static PermissionsXRole reconstitute(Long id, Permissions permiso, Role rol){
        return PermissionsXRole.builder()
            .id(id)
            .permiso(permiso)
            .rol(rol)
        .build();
    }
}
