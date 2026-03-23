package com.taskapi.shared.domain.exceptions;


/**
 * Recibe un String message
 *  Ej -> new BussinesRuleViolation("Usuario ya existe") returns -> "BUINESS_RULE_VIOLATION Usuario ya existe"
 */
public class BussinesRuleException extends DomainException {

    public BussinesRuleException(String code, String message) {
        super("BUSSINES_RULE_VIOLATION", message);
        //TODO Auto-generated constructor stub
    }
    
}
