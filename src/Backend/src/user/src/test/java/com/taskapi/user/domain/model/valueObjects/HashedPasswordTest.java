package com.taskapi.user.domain.model.valueObjects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

/**
 * COBERTURA
 * <p>
 * CASOS DE TEST
 * <ul>
 *      <li>HappyPath { Acierta [ Creacion , NotNull , Matches( ) ] }</li>
 *      <li>Proteccion de Hash en Logs { Acierta [ Creacion , toString() override 'PROTECTED' ] } </li>
 *      <li>Criterios de Aceptacion para una pword { Acierta Thrown [ CREAR NULO , CREAR BLANK , CREAR CORTO ] } </li>
 * </ul>
 */
public class HashedPasswordTest {

    @Test
    @DisplayName("HappyPath --- Should create valid HashedPassword with valid parameters")
    void should_create_valid_password_hash(){
        assertAll(
            ()->{
                HashedPassword hPword = assertDoesNotThrow(()->HashedPassword.of("LONG_ENOUGH_EXAMPLE"));
                assertNotNull(hPword);
                assertTrue(hPword.matches("LONG_ENOUGH_EXAMPLE"));
            }
        );
    }

    @Test
    @DisplayName("Should Protect the value from logging via toString()")
    void should_protect_hash_value_in_logging(){
        assertAll(
            ()->{
                HashedPassword hPword = assertDoesNotThrow(()->HashedPassword.of("LONG_ENOUGH_EXAMPLE"));
                assertEquals("[PROTECTED]", hPword.toString());
            }
        );
    }

    @Test
    @DisplayName("Should thrown when create HashedPassword with invalid properties")
    // Las contraseñas deben tener mas de 8 caracteres y no ser nulas o limpias
    void should_thrown_when_invalid_properties(){
        assertAll(
            ()->{
                assertThrows(InvalidPropertiesGiven.class, ()->HashedPassword.of(null));
                assertThrows(InvalidPropertiesGiven.class, ()->HashedPassword.of(""));
                assertThrows(InvalidPropertiesGiven.class, ()->HashedPassword.of("SHORT"));
            }
        );
    }
}
