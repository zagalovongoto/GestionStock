package org.mambey.gestiondestock.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

@Component
public class ObjectsValidator<T> {
    
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public List<String> validate(T objectToValidate){

        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if(!violations.isEmpty()){
            return violations.stream()
                      .map(ConstraintViolation::getMessage)
                      .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
