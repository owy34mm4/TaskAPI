package com.taskapi.user.infraestructure.persistance.repository.external.mapper;

import org.springframework.stereotype.Component;

import com.taskapi.shared.application.port.out.IExternalMapper;
import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserExternalMapper implements IExternalMapper<User, UserExternalDTO> {

    @Override
    public UserExternalDTO toExternal(User model) {
        return UserExternalDTO.builder()
            .id(model.getId())
            .name(model.getName())
            .username(model.getUsername())
            .email(model.getEmail())
            .password(model.getPassword().getValue())
            .active(model.isActive())
        .build();
    }
    
}
