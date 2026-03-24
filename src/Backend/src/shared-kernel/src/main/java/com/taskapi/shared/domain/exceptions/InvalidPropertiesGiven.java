package com.taskapi.shared.domain.exceptions;

/**
 * <b>PARAMS</b>
 * <ul>
 * <li>String 'ResourceModel' ( Clase que pesenta el Error de Parametros) </li>
 * </ul>
 * <p>
 * <pre> 
 * public class User{
 *      if(propsCheck){throw new InvalidPropertiesGiven("User") }
 * }
 * </pre> 
 * <b>returns/throws</b> 
 * <pre>
 * "INVALID_PROPERTIES_GIVEN User Ha recibido propiedades Invalidas"
 * </pre>
*/
public class InvalidPropertiesGiven extends DomainException{
    public InvalidPropertiesGiven(String resourceModel) {
        super("INVALID_PROPERTIES_GIVEN ", resourceModel + " Ha recibido propiedades Invalidas");
    }
}
