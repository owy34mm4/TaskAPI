package com.taskapi.task.application.useCase.command;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTeamCommand {
    private Long id;

    private String name;

    private LocalDateTime creacion;

    private Long ownerId;

    private String identificationCode;

    private boolean active;
}
