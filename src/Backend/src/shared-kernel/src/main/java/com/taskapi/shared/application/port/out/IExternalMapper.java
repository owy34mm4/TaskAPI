package com.taskapi.shared.application.port.out;

public interface IExternalMapper<DOMAIN, EXTERNAL> {
    EXTERNAL toExternal(DOMAIN model);
}
