package com.taskapi.user.infraestructure.persistance.entity;

import com.taskapi.user.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean active;

    // Mapeo dominio → JPA  
    public static UserTable fromDomain(User user) {  
        return UserTable.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .username(user.getUsername())
            .password(user.getPassword().getValue())
        .build();
    }  
  
    // Mapeo JPA → dominio  
    public User toDomain() {  
        return User.reconstitute(id,name, email , username, password);  
    }
}
