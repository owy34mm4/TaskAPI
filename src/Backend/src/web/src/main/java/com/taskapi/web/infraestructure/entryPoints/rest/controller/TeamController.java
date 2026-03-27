package com.taskapi.web.infraestructure.entryPoints.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskapi.task.application.port.in.ICreateTeamUseCase;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.RequestCreateTeam;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.ResponseCreateTeam;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private final ICreateTeamUseCase createTeamHandler;

    @PostMapping("/create")
    public ResponseEntity<ResponseCreateTeam> createTeam(@RequestBody RequestCreateTeam request) {
        var cmd = request.toCommand();

        var responseModel = createTeamHandler.execute(cmd);

        var response = ResponseCreateTeam.createFromModel(responseModel);
        
        return ResponseEntity.ok(response);
    }
    
    
}
