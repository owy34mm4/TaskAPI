package com.taskapi.task.domain.valueObject;

import java.util.UUID;

import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

import lombok.Getter;

@Getter
public class EquipoCodigoIdentificacion {
    private String value;

    private void validate(String value) {  
        if (value == null || value.isBlank()) {  
            throw new InvalidPropertiesGiven("El código de equipo no puede ser nulo");  
        }  
        try {  
            // Intentamos parsear. Si no es un UUID válido, lanzará IllegalArgumentException  
            UUID.fromString(value);  
        } catch (IllegalArgumentException e) {  
            throw new InvalidPropertiesGiven("El código '" + value + "' no tiene un formato UUID válido");  
        }  
    }

    private EquipoCodigoIdentificacion(String value){
        validate(value);
        this.value = value;
    }

    public static EquipoCodigoIdentificacion generate(){
        return new EquipoCodigoIdentificacion(UUID.randomUUID().toString());
    }
    
    public static EquipoCodigoIdentificacion reconstitute(String oldString){
         return new EquipoCodigoIdentificacion(oldString);
    }
}
