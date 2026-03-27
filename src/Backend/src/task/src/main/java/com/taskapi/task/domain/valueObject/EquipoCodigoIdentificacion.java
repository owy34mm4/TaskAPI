package com.taskapi.task.domain.valueObject;

import java.util.UUID;

import lombok.Getter;

@Getter
public class EquipoCodigoIdentificacion {
    private String value;

    private EquipoCodigoIdentificacion(String value){
        this.value = value;
    }

    public static EquipoCodigoIdentificacion generate(){
        return new EquipoCodigoIdentificacion(UUID.randomUUID().toString());
    }
    
    public static EquipoCodigoIdentificacion reconstitute(String oldString){
         return new EquipoCodigoIdentificacion(oldString);
    }
}
