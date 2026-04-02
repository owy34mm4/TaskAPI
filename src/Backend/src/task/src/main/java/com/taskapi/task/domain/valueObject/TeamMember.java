package com.taskapi.task.domain.valueObject;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamMember {
    UserExternalDTO member;
    Long memberId;

    public static TeamMember create (Long memberId, UserExternalDTO member){
        return TeamMember.builder()
            .member(member)
            .memberId(memberId)
        .build();
    }
    
    
}
