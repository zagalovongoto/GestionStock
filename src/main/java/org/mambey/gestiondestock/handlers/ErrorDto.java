package org.mambey.gestiondestock.handlers;

import java.util.ArrayList;
import java.util.List;

import org.mambey.gestiondestock.exception.ErrorCodes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorDto {
    
    private Integer httpCode;

    private ErrorCodes code;

    private String message;

    private List<String> errors ;//= new ArrayList<>();

    public ErrorDto(){
        this.errors = new ArrayList<>();
    }

}
