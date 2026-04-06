package com.taskapi.shared.application.port.out;

public interface IDataPort <Info> {
    Info save (Info data);
    Info findById(Long id);
    boolean existsById(Long id);
    
}
