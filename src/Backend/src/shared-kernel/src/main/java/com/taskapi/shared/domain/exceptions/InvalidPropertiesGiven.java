package com.taskapi.shared.domain.exceptions;

/**
 * 
 * Constructor Recibe 1 String -> ResourceModel
 * 
 * Hace referencia al Nombre de la clase que presentó el error de parametros

 * EJ -> throw new InvalidPropertiesGiven("ShoppingCart") returns "INVALID_PROPERTIES_GIVEN ShoppingCart Ha recibido propiedades Invalidas""
*/
public class InvalidPropertiesGiven extends DomainException{
    public InvalidPropertiesGiven(String resourceModel) {
        super("INVALID_PROPERTIES_GIVEN ", resourceModel + " Ha recibido propiedades Invalidas");
    }
}
