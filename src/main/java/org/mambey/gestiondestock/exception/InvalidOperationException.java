package org.mambey.gestiondestock.exception;

import java.util.List;

import lombok.Getter;

public class InvalidOperationException extends RuntimeException{
    
    @Getter
    private ErrorCodes errorCode;
    
    @Getter
    private List<String> errors;

    public InvalidOperationException(String message){
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause){
        super(message, cause);
    }

    public InvalidOperationException(String message, Throwable cause, ErrorCodes errorCode){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public InvalidOperationException(String message, ErrorCodes errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public InvalidOperationException(String message, ErrorCodes errorCode, List<String> errors){
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
