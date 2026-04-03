package com.taskapi.task.domain.valueObject;

import com.taskapi.shared.application.port.out.user.UserExternalDTO;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;

import lombok.Getter;

@Getter
public class TeamMember {
    UserExternalDTO member;
    Long memberId;

    
    public TeamMember(Long memberId, UserExternalDTO member){
        if (memberId == null || member==null || memberId<=0) {throw new InvalidPropertiesGiven("TeamMember ");}

        matchesMemberData(memberId, member);
        this.member= member;
        this.memberId= memberId;
    }

    /**Aquí nos aseguramos que haya coherencia entre la informacion del VO
     *
     */
    private void matchesMemberData(Long memberId, UserExternalDTO member){
        if(! memberId.equals(member.getId()) ){throw new BussinesRuleException("Cant assign incoherent data");}
    }

    
    public static TeamMember create (Long memberId, UserExternalDTO member){
        return new TeamMember(memberId, member);
    }
    
    
}
