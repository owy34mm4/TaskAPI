package com.taskapi.web.infraestructure.entryPoints.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taskapi.task.application.port.in.ICreateTeamUseCase;
import com.taskapi.task.application.port.in.IJoinMemberToTeamUseCase;
import com.taskapi.task.application.useCase.command.JoinMemberToTeamCommand;
import com.taskapi.task.application.useCase.command.addMemberToTeamCommand;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.addMemberToTeam.ResponseAddMemberToTeam;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.createTeam.RequestCreateTeam;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.createTeam.ResponseCreateTeam;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.joinUserToTeam.ResponseJoinMemberToTeam;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private final ICreateTeamUseCase createTeamHandler;
    private final IJoinMemberToTeamUseCase joinMemberToTeamHandler;

    @PostMapping("/create")
    public ResponseEntity<ResponseCreateTeam> createTeam(@RequestBody RequestCreateTeam request) {
        var cmd = request.toCommand();

        var responseModel = createTeamHandler.execute(cmd);

        var response = ResponseCreateTeam.createFromModel(responseModel);
        
        return ResponseEntity.ok(response);
    }
    
    // TouchUrl : User hace peticion y se une automaticamente
    @PostMapping("/joinTeam/{team_code}")
    public ResponseEntity<ResponseJoinMemberToTeam> joinToATeam(@PathVariable("team_code")String teamCode) {
        var cmd = JoinMemberToTeamCommand.of(teamCode);

        joinMemberToTeamHandler.execute(cmd);
        
        var response = ResponseJoinMemberToTeam.builder().status(true).build();
        return ResponseEntity.ok(response);
    }

    // RequestUrl : User peticiona con una lista de Ids que representa los usuarios que va a agregar
    @PostMapping("/addMembers/{team_code}")
    public ResponseEntity<ResponseAddMemberToTeam> addMembersToTeam(@PathVariable("team_code")String teamCode) {
        var cmd = addMemberToTeamCommand.of(teamCode);
        
        return ResponseEntity.ok(null);
    }
    
    
}
