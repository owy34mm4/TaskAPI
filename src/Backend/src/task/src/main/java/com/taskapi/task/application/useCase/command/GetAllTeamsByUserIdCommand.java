package com.taskapi.task.application.useCase.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetAllTeamsByUserIdCommand {
    private Integer userId;

    public static GetAllTeamsByUserIdCommand of (String userId){
        return new GetAllTeamsByUserIdCommand(Integer.getInteger(userId));
    }

    public static GetAllTeamsByUserIdCommand generate (){
        return new GetAllTeamsByUserIdCommand();
    }
}
