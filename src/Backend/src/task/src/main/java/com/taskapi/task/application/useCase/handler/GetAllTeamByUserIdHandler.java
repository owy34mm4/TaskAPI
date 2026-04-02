package com.taskapi.task.application.useCase.handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.taskapi.shared.application.port.out.user.IUserDataPort;
import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.task.application.port.in.IGetAllTeamsByUserId;
import com.taskapi.task.application.port.out.ITeamRepository;
import com.taskapi.task.application.port.out.IUserXRoleXTeamRepository;
import com.taskapi.task.application.useCase.command.GetAllTeamsByUserIdCommand;
import com.taskapi.task.domain.model.Team;
import com.taskapi.task.domain.valueObject.TeamMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllTeamByUserIdHandler implements IGetAllTeamsByUserId {

    private final ICurrentUser currentUser;
    private final IUserDataPort userRepository;
    private final ITeamRepository teamRepository;
    private final IUserXRoleXTeamRepository relationalRespository;


    @Override
    public List<Team> execute(GetAllTeamsByUserIdCommand cmd) {
        Long requesterId = currentUser.getId();
        if(!userRepository.existsById(requesterId)){throw new BussinesRuleException("Usuario Invalido");}
        
        //Obtenemos la informacion raw de los equipos que necesitamos
            var rawTeamsData = teamRepository.findAllByUserId(requesterId);
            List<Long> teamIds = rawTeamsData.stream().map(Team::getId).toList();

        //Inyectamos la informacion del dueño en la rawData que teniamos (Ensamblamos nuestra respuesta)
            rawTeamsData.stream().map(item -> item.getOwner()
                .inyectOwnerData(
                    userRepository.findById(item.getOwner().getOwnerId())
                )
            ).toList();

        // Agrupar memberIds por teamId  
            Map<Long, List<Long>> memberIdsByTeam = relationalRespository 
                .findMemberIdsByTeamIds(teamIds)  
                .stream()  
                .collect(Collectors.groupingBy(  
                    row -> (Long) row[0],  
                    Collectors.mapping(row -> (Long) row[1], Collectors.toList())  
                ));
        
        // Fetchear todos los DTOs externos (1 sola llamada si tu puerto lo soporta)  
            List<Long> allMemberIds = memberIdsByTeam.values()  
                .stream().flatMap(List::stream).distinct().toList();
        
        
        Map<Long, UserExternalDTO> usersById = userRepository  
            .findAllById(allMemberIds)        
            .stream()  
            .collect(Collectors.toMap(UserExternalDTO::getId, u -> u));

        //Inyectamos la data de los miembros a cada equipo
            rawTeamsData.stream().forEach(team ->{
                List<TeamMember> members = memberIdsByTeam.getOrDefault(team.getId(), List.of())
                .stream()
                .map(memberId -> TeamMember.create(memberId, usersById.get(memberId))).toList();

                team.inyectMembers(members);
            });
            
        
        return rawTeamsData;
    }
    
}
