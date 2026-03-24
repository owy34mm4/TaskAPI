package com.taskapi.e2e.scenarios;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.taskapi.e2e.BaseE2E;
import com.taskapi.web.infraestructure.entryPoints.dto.user.createUser.ResponseCreateUser;

public class UserE2E extends BaseE2E{

/// Test 1 : Crear Usuario Nuevo
    @Test
    @DisplayName("Escenario: Requester desautorizado crea un usuario | Register User")
    void unauthorized_creates_user(){
        ResponseCreateUser response = createUser("usernamePruebaE2E", "PasswordPruebaE2E").getBody();
        assertNotNull(response);
    }

    @Test
    @DisplayName("Escenario: Requester Desautorizado obtiene autorizacion | Login User")
    void unauthorized_gets_authorization(){
        createUser("usernamePruebaE2E", "PasswordPruebaE2E");

        String response = login("usernamePruebaE2E", "PasswordPruebaE2E");
        assertNotNull(response);

    }
    
}
