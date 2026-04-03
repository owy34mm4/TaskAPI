package com.taskapi.task.domain.valueObject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

/**
 * COBERTURA
 * <p>
 * CASOS DE TEST
 * <ul>
 *      <li>HappyPath Creation { Acierta [Creacion, NotNull ] } </li>
 *      <li>HappyPath Reconstitute { Acierta [Creacion, Reconstittucion, Matcheo() ] } </li>
 *      <li>Reconstitucion invalida { Acierta thrown [REGEN NULO , REGEN BLANK , REGEN INVALIDO ] } </li>
 * </ul>
 */
public class TeamIdentificationCodeTest{

/// Test1 ---- Create a valid IdentificationCode
    @Test
    @DisplayName("HappyPath ---- Should create valid identificaitonCode")
    void should_create_valid_identification_code(){
        assertAll(
            ()->{
                var data = assertDoesNotThrow(()-> EquipoCodigoIdentificacion.generate());

                assertNotNull(data);
            }
        );
    }

/// Test2 ---- Reconstitute correct IdentificationCode
    @Test
    @DisplayName("HappyPath ---- Should recreate same identification code as creation")
    void should_recreate_correct_identification_code(){
        assertAll(
            ()->{
                var data = assertDoesNotThrow(()-> EquipoCodigoIdentificacion.generate());
                var reconstituteData = assertDoesNotThrow(()-> EquipoCodigoIdentificacion.reconstitute(data.getValue()));

                assertEquals(data.getValue(), reconstituteData.getValue());
            }
        );
    }

/// Test3 ---- ShouldThrownByInvalidProperties
    @Test
    @DisplayName("Should Thrown when invalid properties")
    void should_thrown_when_invalid_properties(){
        assertAll(
            ()->{
                assertThrows(InvalidPropertiesGiven.class, ()-> EquipoCodigoIdentificacion.reconstitute(null));
                assertThrows(InvalidPropertiesGiven.class, ()-> EquipoCodigoIdentificacion.reconstitute(""));
                assertThrows(InvalidPropertiesGiven.class, ()-> EquipoCodigoIdentificacion.reconstitute("aa"));
            }
        );

    }
}