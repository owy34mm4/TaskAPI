package com.taskapi.user.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTest {
    
    private User buildDefaultValidUser(){
        return User.create(
            "test@test.com", 
            "Raul Jose", 
            "testUsername", 
            "plainTextPWORD"
        );
    } 
    
        
    private User reconstituteDefaultValidUser(){

        String hashedPassword = new BCryptPasswordEncoder()  
            .encode("plainTestLongEnough");

        return User.reconstitute(
            1L,
            "test@test.com", 
            "Raul Jose", 
            "testUsername", 
            hashedPassword
        );
    }

    @Test
    @DisplayName("HappyPath --- Should Create Valid User With Valid Properties")
    void should_create_valid_user(){
        assertAll(
            ()->{
                User user = assertDoesNotThrow((()->buildDefaultValidUser()));
                assertNotNull(user);

                assertEquals(true, user.isActive());
            }
        );
    }

    @Test
    @DisplayName("HappyPath ---- Should Reconstitute Valid User With Valid Properties")
    void should_reconstitute_sucesfully(){
        assertAll(
            ()->{
                User user = assertDoesNotThrow(()->reconstituteDefaultValidUser());
                assertNotNull(user);
                assertNotEquals("plainTextPWORD", user.getPassword().getValue());
            }
        );
    }
}
