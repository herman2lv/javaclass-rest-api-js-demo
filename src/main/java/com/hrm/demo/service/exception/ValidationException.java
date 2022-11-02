package com.hrm.demo.service.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

public class ValidationException extends ClientException {
    @Getter
    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

}
