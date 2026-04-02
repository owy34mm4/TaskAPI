package com.taskapi.shared.application.port.out.user;

import java.util.List;

import com.taskapi.shared.application.port.out.IDataPort;

public interface IUserDataPort extends IDataPort<UserExternalDTO> {
    List<UserExternalDTO> findAllById(List<Long> ids);
    // List<Object[]> findMemberIdsByTeamIds(List<Long> ids);
}
