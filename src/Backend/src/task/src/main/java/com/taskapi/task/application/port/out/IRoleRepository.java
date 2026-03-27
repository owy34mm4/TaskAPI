package com.taskapi.task.application.port.out;

public interface IRoleRepository {
    Long findIdByCode(String code);
}
