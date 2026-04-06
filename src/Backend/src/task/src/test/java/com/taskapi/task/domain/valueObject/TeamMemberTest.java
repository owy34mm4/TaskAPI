package com.taskapi.task.domain.valueObject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

/**COBERTURA
 * <p>
 * CASOS DE TEST
 * <ul>
 *      <li>HappyPath Creation { Acierta DoesntThrow [ Create , NotNull ] }</li>
 *      <li>Check Invalid Data { Acierta Thrown [ CreateNull , CreateWithInvalidIds ] } </li>
 *      <li>Check Incoherent Data { Acierta Thrown [ CreateWhitIncoherentData ] }</li>
 * </ul>
 */
public class TeamMemberTest {

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

// Test 1 : HappyPath --- Should create sucessfully with valid data
    @Test
    @DisplayName("HappyPath --- Should Create Sucesfully")
    void should_create_sucesfully(){
        assertAll(
            ()->{
                var member = createDefaultOwnerData();

                var data = assertDoesNotThrow(()-> TeamMember.create(member.getId(), member));
                assertNotNull(data);
            }
        );
    }

//Test 2 : Should Thrown When Invalid Data (No Incoherente)
    @Test
    @DisplayName("Should Thrown When Invalid Data; Doesnt Check for Incoherent Data")
    void should_thrown_when_invalid_properties(){
        assertAll(
            ()->{
                var member = createDefaultOwnerData();

                assertThrows(InvalidPropertiesGiven.class, ()->TeamMember.create(null, null));
                assertThrows(InvalidPropertiesGiven.class, ()->TeamMember.create(0L, member));
                assertThrows(InvalidPropertiesGiven.class, ()->TeamMember.create(-1L, member));
            }
        );
    }

//Test 3 : Should Thrown When Invalid Data (No Incoherente)
    @Test
    @DisplayName("Should Thrown When IncoherentData; Doesnt Check for Invalid")
    void should_thrown_when_incoherent_data(){
        assertAll(
            ()->{
                var member = createDefaultOwnerData();

                assertThrows(BussinesRuleException.class, ()->TeamMember.create(2L, member));
            }
        );
    }
    
}
