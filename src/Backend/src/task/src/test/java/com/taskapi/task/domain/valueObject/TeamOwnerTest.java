package com.taskapi.task.domain.valueObject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

/**COBERTURA
 * <p>
 * CASOS DE TEST
 * <ul>
 *      <li>HappyPath Creation {Acierta [ CREATE , NOTNULL ] }</li>
 *      <li>HappyPath Inyection {Acierta [ CREATE , NOTNULL , Matches ()  ] }</li>
 *      <li>Criterios de Negocio { Acierta thrown [ CrearNull , CrearInvalido , InyectarNull ] }</li>
 * </ul>
 * 
 * 
 */
public class TeamOwnerTest {
    
    //Helpers ------------
    private UserExternalDTO createDefaultOwnerData(){
        return UserExternalDTO.builder()
            .id(1L)
            .name("Name")
            .email("test@test.com")
            .username("validUsername")
            .password("contraseñaPrueba")
            .active(true)
        .build();
    }

/// Test 1 : HappyPath --- Should create sucesfully
    @Test
    @DisplayName("HappyPath -- Should Create Sucessfully")
    void should_create_sucesfully(){
        assertAll(
            ()->{
                var data = assertDoesNotThrow(()->TeamOwner.create(1L));
                assertNotNull(data);
            }
        );
    }

/// Test 2 : HappyPath --- Should inyect ownerData sucesfully
    @Test
    @DisplayName("HappyPath --- Should Inyect Ownerdata sucesfully")
    void should_inyect_ownerData_sucesfully(){
        assertAll(
            ()->{
                var data = assertDoesNotThrow(()->TeamOwner.create(1L));
                var owner = createDefaultOwnerData();

                assertDoesNotThrow(()->data.inyectOwnerData(owner));
                assertNotNull(data);

                assertEquals(owner.getId(), data.getOwnerData().getId());
                assertEquals(owner.getName(), data.getOwnerData().getName());
                assertEquals(owner.getUsername(), data.getOwnerData().getUsername());
            }
        );
    }

/// Test 3 : Should Thrown when invalid properties
    @Test
    @DisplayName("Should thrown when invalid data given")
    void shoul_thrown_when_invalid_properties(){
        assertAll(
            ()->{
                assertThrows(InvalidPropertiesGiven.class, ()->TeamOwner.create(null));
                assertThrows(InvalidPropertiesGiven.class, ()->TeamOwner.create(0L));
                assertThrows(InvalidPropertiesGiven.class, ()->TeamOwner.create(-999L));

                var data = TeamOwner.create(1L);
                assertThrows(InvalidPropertiesGiven.class, ()->data.inyectOwnerData(null));
                
            }
        );

    }
    
}
