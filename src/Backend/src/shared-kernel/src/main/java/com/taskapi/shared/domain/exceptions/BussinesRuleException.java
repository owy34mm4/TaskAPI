package com.taskapi.shared.domain.exceptions;


/**
 * <ul>
 * PARAMS
 * <li>String message</li>
 * <pre>
 *  new BussinesRuleViolation("Usuario ya existe")
 * </pre>
 * <b>returns</b>
 * <br>
 * <pre>
 * "BUINESS_RULE_VIOLATION Usuario ya existe"
 * </pre>
 */
public class BussinesRuleException extends DomainException {

    public BussinesRuleException( String message) {
        super("BUSSINES_RULE_VIOLATION", message);
    }
    
}
