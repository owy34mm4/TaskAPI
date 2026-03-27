package com.taskapi.web.infraestructure.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.shared.domain.exceptions.InvalidPropertiesGiven;
import com.taskapi.shared.domain.exceptions.NotFoundException;
import com.taskapi.web.infraestructure.exception.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(  
        HttpStatus status,  
        String message,  
        String path,
        Object stack
    ) {  
        return ResponseEntity.status(status).body(  
            new ErrorResponse(  
                status.value(),  
                status.getReasonPhrase(),  
                message,  
                path,  
                LocalDateTime.now(),
                stack

            )  
        );  
    }
    

    @ExceptionHandler(InvalidPropertiesGiven.class)
    public ResponseEntity<ErrorResponse> handleInvalidPropertiesGiven(
        InvalidPropertiesGiven ex,
        HttpServletRequest request
    ){
        return buildResponse(  
            HttpStatus.CONFLICT,  
            ex.getMessage(),  
            request.getRequestURI(),
            ex.getStackTrace()
        );

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
        NotFoundException ex,
        HttpServletRequest request
    ){
        return buildResponse(  
            HttpStatus.CONFLICT,  
            ex.getMessage(),  
            request.getRequestURI(),
            ex.getStackTrace()
        );

    }

    @ExceptionHandler(BussinesRuleException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
        BussinesRuleException ex,
        HttpServletRequest request
    ){
        return buildResponse(  
            HttpStatus.CONFLICT,  
            ex.getMessage(),  
            request.getRequestURI(),
            ex.getStackTrace()
        );

    }

    @ExceptionHandler(Exception.class)  
    public ResponseEntity<ErrorResponse> handleGeneric(  
        Exception ex,  
        HttpServletRequest request  
    ) {  
        return buildResponse(  
            HttpStatus.INTERNAL_SERVER_ERROR,  
            "Unexpected error",  
            request.getRequestURI(),
            ex.getStackTrace()
        );  
    }

   
}
