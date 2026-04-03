package com.taskapi.task.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

public class TeamTest {
    
    // Helpers --------
    Team reconstituteDefaultTeam(){
        var team = Team.create("testName", 1L);
        return Team.reconstitute(
            1L, 
            team.getCreacion(), 
            team.getName(),
            team.getOwner().getOwnerId(), 
            team.getCodigo_identificacion().getValue(),
            team.isActive()
            );
    }

    Team buildDefaultTeam(){
        return Team.create(
            "testName", 
            1L
        );
        
    }

// Test 1 : HappyPath ----- Should Create Valid team with Valid Properties
    @Test
    @DisplayName("HappyPath ----  Should Create a Valid Team Sucessfully")
    void should_create_sucesfully(){
        assertAll(
            ()->{
                var team = assertDoesNotThrow(()-> buildDefaultTeam());

                assertNotNull(team);
                assertEquals(null, team.getId());
                assertEquals("testName", team.getName());
                assertInstanceOf(LocalDateTime.class, team.getCreacion());
                assertNotNull(team.getCodigo_identificacion());
                assertEquals(true, team.isActive());
            }
        );
    }

// Test 2 : HappyPath ---- Should Reconstitute Valid Team With Coherent Data
    @Test
    @DisplayName("HappyPath ---- Should Reconstitute a valid team with coherent data")
    void should_reconstitute_valid_team_with_coherent_data(){
        assertAll(
            ()->{
                var team = reconstituteDefaultTeam();

                assertNotNull(team);
                assertEquals(1L, team.getId());
                assertEquals("testName", team.getName());
                assertInstanceOf(LocalDateTime.class, team.getCreacion());
                assertNotNull(team.getCodigo_identificacion());
                assertEquals(true, team.isActive());

            }
        );
    }

// Test 3 : Should Thrown when create with invalid properties
    @Test
    @DisplayName("Should Thrown When Create With Invalid Properties")
    void should_thrown_when_create_with_invalid_properties(){
        assertAll(
            ()->{
                assertThrows(InvalidPropertiesGiven.class, ()->Team.create(null, null));
                assertThrows(InvalidPropertiesGiven.class, ()->Team.create("TestName", null));
                assertThrows(InvalidPropertiesGiven.class, ()->Team.create(null, 1L));
            }
        );

    }

// Test 4 : Should Thrown when Reconstitute with invalid properties
    @Test
    @DisplayName("Should Thrown When Reconstitute With Invalid Properties")
    void should_thrown_when_reconstitute_invalid_properties(){
        assertAll(
            ()->{
                assertThrows(InvalidPropertiesGiven.class, ()->Team.reconstitute(
                    null, 
                    null,
                    null, 
                    null, 
                    null, 
                    false)
                    );
                
                assertThrows(InvalidPropertiesGiven.class, ()->Team.reconstitute(
                    0L, 
                    null,
                    null, 
                    null, 
                    "invalidUUID", 
                    false)
                    );
            }
        );

    }
}
