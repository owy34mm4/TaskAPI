package com.taskapi.task.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;
import com.taskapi.task.domain.valueObject.EquipoCodigoIdentificacion;
import com.taskapi.task.domain.valueObject.TeamMember;
import com.taskapi.task.domain.valueObject.TeamOwner;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Team {

    private Long id;

    private String name;

    private LocalDateTime creacion;

    private TeamOwner owner;

    private EquipoCodigoIdentificacion codigo_identificacion;

    private boolean active;

    private List<TeamMember> members;


    public static Team create(String name, Long owner_id){
        if(name==null || owner_id==null){throw new InvalidPropertiesGiven("Team ");}
        return Team.builder()
            .id(null)
            .name(name)
            .creacion(LocalDateTime.now())
            .owner(TeamOwner.create(owner_id))
            .codigo_identificacion(EquipoCodigoIdentificacion.generate())
            .active(true)
        .build();
    }

    public static Team reconstitute(Long id, LocalDateTime creacion, String name, Long owner_id, String oldIdentificationCode, boolean active){
        if(id==null || creacion==null || name==null || name.isBlank() || id<=0){throw new InvalidPropertiesGiven("Team ");}
        return Team.builder()
            .id(id)
            .name(name)
            .creacion(creacion)
            .owner(TeamOwner.create(owner_id))
            .codigo_identificacion(EquipoCodigoIdentificacion.reconstitute(oldIdentificationCode))
            .members(new ArrayList<>())
            .active(active)
        .build();
    }

    public void inyectMembers(List<TeamMember> members){
        this.members = members;
        
    }

    
    
}
