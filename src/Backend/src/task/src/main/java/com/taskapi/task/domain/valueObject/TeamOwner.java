package com.taskapi.task.domain.valueObject;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 * <h1>Disclaimer de usop en este VO</h1>
 * 
 * <p> 
 * TeamOwner tiene solo 1 parametro obligatorio para sus creaciones {ownerId}
 * <br>
 * {OwnerData} puede ser inyectado via metodos
 * 
 * </p>
*/
public class TeamOwner {
    
    private UserExternalDTO ownerData;

    private final Long ownerId;

    
    public static TeamOwner create( Long ownerId){
        if (ownerId==null || ownerId<=0){ throw new InvalidPropertiesGiven("TeamOwner ");}
        return TeamOwner.builder()
            .ownerId(ownerId)
        .build();
    }

    public TeamOwner inyectOwnerData(UserExternalDTO ownerData){
        if (ownerData==null) {throw new InvalidPropertiesGiven("TeamOwner ");}
        this.ownerData = ownerData;
        return this;
    }


}
