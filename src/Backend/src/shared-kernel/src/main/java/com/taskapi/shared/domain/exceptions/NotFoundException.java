package com.taskapi.shared.domain.exceptions;

/**
 * 
 * Recibe parametro String que refiere al nombre dle recurso que no se pudo encontrar
 * 
 * ej -> new NotFoundException("Order") returns "NOT_FOUND Order Not Found in Repos"
 */
public class NotFoundException extends DomainException{

    public NotFoundException( String resource) {
        super("NOT_FOUND", resource + "Not Found in Repos");
    }
    
}
