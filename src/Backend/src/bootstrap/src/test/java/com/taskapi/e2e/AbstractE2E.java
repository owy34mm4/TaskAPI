package com.taskapi.e2e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.taskapi.shared.domain.IntegrationTest;

@IntegrationTest
public abstract class AbstractE2E  {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
    .withDatabaseName("arka_test")
    .withUsername("test")
    .withPassword("test")
    .withInitScript("bootstrap/src/test/resources/sql/init_admin.sql");

    @DynamicPropertySource  
    static void properties(DynamicPropertyRegistry registry) {  
        registry.add("spring.datasource.url", mysql::getJdbcUrl);  
        registry.add("spring.datasource.username", mysql::getUsername);  
        registry.add("spring.datasource.password", mysql::getPassword);  
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired  
    protected TestRestTemplate restTemplate; 
}
