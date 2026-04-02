package com.taskapi.web.infraestructure.entryPoints.dto.teams.getAllByUserId;

import java.util.List;

import com.taskapi.task.domain.model.Team;
import com.taskapi.web.infraestructure.entryPoints.dto.teams.getAllByUserId.dtos.TeamDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseGetAllTeamsByUserId {

    private List<TeamDTO> equipos;
    
    public static ResponseGetAllTeamsByUserId createFromDomainTeams(List<Team> domainTeams){
        return new ResponseGetAllTeamsByUserId(
            domainTeams.stream().map(
                item->TeamDTO.dumpFromDomain(item)
            ).toList()
        );
    }


    
}
