package org.mambey.gestiondestock.handlers;

import org.mambey.gestiondestock.exception.EntityAlreadyExistsException;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)//Permet à open api d'inclure ces réponse d'erreurs
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleExcEntity(EntityNotFoundException exception, WebRequest webRequest){

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
            .code(exception.getErrorCode())
            .httpCode(notFound.value())
            .message(exception.getMessage())
            .build();

            return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleExcEntity(InvalidEntityException exception, WebRequest webRequest){

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
            .code(exception.getErrorCode())
            .httpCode(badRequest.value())
            .message(exception.getMessage())
            .errors(exception.getErrors())
            .build();

            return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleExcEntity(EntityAlreadyExistsException exception, WebRequest webRequest){

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
            .code(exception.getErrorCode())
            .httpCode(badRequest.value())
            .message(exception.getMessage())
            .build();

            return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorDto> handleExcEntity(InvalidOperationException exception, WebRequest webRequest){

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
            .code(exception.getErrorCode())
            .httpCode(badRequest.value())
            .message(exception.getMessage())
            .errors(exception.getErrors())
            .build();

            return new ResponseEntity<>(errorDto, badRequest);
    }

    //ON fait une redéfinition de la méthode ci-dessous pour pouvoir gérer les erreurs de type mismatch lors de la désériaisation
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ErrorDto errorDto = ErrorDto.builder()
            .code(ErrorCodes.INVALID_DATA)
            .httpCode(status.value())
            //.message(ex.getMessage())
            .message("Type de données invalide")
            .build();
		return handleExceptionInternal(ex, errorDto, headers, status, request);
	}

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ErrorDto errorDto = ErrorDto.builder()
        .code(ErrorCodes.INVALID_DATA)
        .httpCode(status.value())
        //.message(ex.getMessage())
        .message("Arguments invalides")
        .build();

		return handleExceptionInternal(ex, errorDto, headers, status, request);
	}

}
