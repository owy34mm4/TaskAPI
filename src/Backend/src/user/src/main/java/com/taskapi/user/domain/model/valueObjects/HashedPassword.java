package com.taskapi.user.domain.model.valueObjects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

import lombok.Getter;

@Getter
public class HashedPassword {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();  
  
    private final String value;

    private HashedPassword(String hashedValue){
        this.value = hashedValue;
    }

    /*Recibe un String y lo Hashea*/ 
    public static HashedPassword of (String plainPassword){
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new InvalidPropertiesGiven("HashedPassword");
        }
        if(plainPassword.length()<=8){
            throw new InvalidPropertiesGiven("HashedPassword");
        }

        return new HashedPassword(ENCODER.encode(plainPassword));
    }

    /*Recibe un Hash y lo Setea directamente*/
    public static HashedPassword fromHash(String existingHash){
        return new HashedPassword(existingHash);
    }

    /*Verifica si un password plano coincide con hash*/
    public boolean matches(String plainPassword){
        return ENCODER.matches(plainPassword, this.value);
    }

    /*Proteccion de Informacion */
    @Override  
    public String toString() {  
        return "[PROTECTED]";  
    }
}
