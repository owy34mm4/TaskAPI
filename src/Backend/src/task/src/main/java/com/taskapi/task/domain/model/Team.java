package com.taskapi.task.domain.model;

import java.time.LocalDateTime;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.task.domain.valueObject.EquipoCodigoIdentificacion;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Team {

    private Long id;

    private String name;

    private LocalDateTime creacion;

    private Long owner_id;

    private UserExternalDTO owner;

    private EquipoCodigoIdentificacion codigo_identificacion;

    private boolean active;


    public static Team create(String name, Long owner_id){
        return Team.builder()
            .id(null)
            .name(name)
            .creacion(LocalDateTime.now())
            .owner_id(owner_id)
            .codigo_identificacion(EquipoCodigoIdentificacion.generate())
            .active(true)
        .build();
    }

    public static Team reconstitute(Long id, LocalDateTime creacion, String name, Long owner_id, String oldIdentifier, boolean active){
        return Team.builder()
            .id(id)
            .name(name)
            .creacion(creacion)
            .owner_id(owner_id)
            .codigo_identificacion(EquipoCodigoIdentificacion.reconstitute(oldIdentifier))
            .active(active)
        .build();
    }
    
}
