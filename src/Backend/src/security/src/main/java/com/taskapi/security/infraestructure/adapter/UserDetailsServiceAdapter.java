package com.taskapi.security.infraestructure.adapter;

// import java.util.List;

// import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.taskapi.user.application.port.out.IUserRepository;
import com.taskapi.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceAdapter implements UserDetailsService {
    private final IUserRepository userRepository;  
  
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  
        User user = userRepository.findByEmail(email);  
  
        return org.springframework.security.core.userdetails.User.builder()  
            .username(user.getEmail())  
            .password(user.getPassword().getValue())  
            // .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol().name())))  
            .build();  
    }
}
