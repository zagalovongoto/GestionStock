package org.mambey.gestiondestock.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException{
    
    private ErrorCodes errorCode;

    public EntityAlreadyExistsException(String message){
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }

    public EntityAlreadyExistsException(String message, Throwable cause, ErrorCodes errorCode){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public EntityAlreadyExistsException(String message, ErrorCodes errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
