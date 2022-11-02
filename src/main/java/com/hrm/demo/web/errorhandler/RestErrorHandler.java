package com.hrm.demo.web.errorhandler;

import com.hrm.demo.service.dto.ErrorDto;
import com.hrm.demo.service.dto.ValidationResultDto;
import com.hrm.demo.service.exception.ClientException;
import com.hrm.demo.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RestControllerAdvice("com.hrm.demo.web.rest")
public class RestErrorHandler {

    private static final String SERVER_ERROR = "Server Error";
    private static final String CLIENT_ERROR = "Client Error";
    private static final String SERVER_ERROR_DEFAULT_MESSAGE = "Sorry, We are working on...";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationResultDto validationError(ValidationException e) {
        Map<String, List<String>> errors = mapErrors(e.getErrors());
        return new ValidationResultDto(errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto error(ClientException e) {
        return new ErrorDto(CLIENT_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(Exception e) {
        return new ErrorDto(SERVER_ERROR, SERVER_ERROR_DEFAULT_MESSAGE);
    }

    private Map<String, List<String>> mapErrors(Errors rawErrors) {
        return rawErrors.getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                        )
                );
    }
}
