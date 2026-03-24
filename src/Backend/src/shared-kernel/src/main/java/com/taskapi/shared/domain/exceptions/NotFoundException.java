package com.taskapi.shared.domain.exceptions;

/**
 * <ul>
 * <li>String 'resource' : refiere al nombre del recurso que no se pudo encontrar</li>
 * </ul>
 * <br>
 * <pre>
 * repository.findById(id)
            .orElseThrow(()-> new NotFoundException("User")).toDomain();
 * </pre>
 * <b>returns</b>
 * <pre>
 * "NOT_FOUND Order Not Found in Repos
 * </pre>
 */
public class NotFoundException extends DomainException{

    public NotFoundException( String resource) {
        super("NOT_FOUND", resource + "Not Found in Repos");
    }
    
}
