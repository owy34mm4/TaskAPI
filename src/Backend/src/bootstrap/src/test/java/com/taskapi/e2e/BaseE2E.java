package com.taskapi.e2e;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.taskapi.bootstrap.TaskAPIApplication;
import com.taskapi.web.infraestructure.entryPoints.dto.user.createUser.RequestCreateUser;
import com.taskapi.web.infraestructure.entryPoints.dto.user.createUser.ResponseCreateUser;
import com.taskapi.web.infraestructure.entryPoints.dto.user.logInUser.RequestLogInUser;
import com.taskapi.web.infraestructure.entryPoints.dto.user.logInUser.ResponseLogInUser;

@SpringBootTest(
    classes = TaskAPIApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
public class BaseE2E extends AbstractE2E{

    // ─── HELPERS ─────────────────────────────────────────────  
    protected HttpHeaders jsonHeaders() {  
        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_JSON);  
        return headers;  
    }  
  
    protected HttpHeaders authHeaders(String token) {  
        HttpHeaders headers = jsonHeaders();  
        headers.setBearerAuth(token); // "Authorization: Bearer <token>"  
        return headers;  
    }  

    //─── Auth ─────────────────────────────────────────────  

    protected String login(String username, String password) {  
        RequestLogInUser req = RequestLogInUser.builder()
            .username_or_email(username)
            .password(password)
        .build();  
  
        ResponseEntity<ResponseLogInUser> response = restTemplate.postForEntity(  
            "/api/v0/auth/login",  
            new HttpEntity<>(req, jsonHeaders()),  
            ResponseLogInUser.class  
        );  
  
        return response.getBody().getToken().toString(); // Devuelve el JWT  
    }  

    protected String loginInAdmin(){
        return login("admin", "admin123"); //<-- Se establecen en el 'init_admin.sql'
    }


    protected ResponseEntity<ResponseCreateUser> createUser(String username, String password){
        RequestCreateUser request = RequestCreateUser.builder()
            .name("nombreTest")
            .username(username)
            .email("test@test.com")
            .password(password)
        .build();

        return restTemplate.postForEntity(
            "api/v1/auth/register", 
            new HttpEntity<>(request, jsonHeaders()), 
            ResponseCreateUser.class
        );

    }
    

}
