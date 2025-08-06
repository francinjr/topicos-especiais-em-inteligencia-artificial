package com.francinjr.rentalbusiness.commons.infrastructure.exceptionhandler;

import com.francinjr.rentalbusiness.components.users.InvalidTokenException;
import com.francinjr.rentalbusiness.components.users.UsernameOrPasswordInvalidException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.francinjr.rentalbusiness.commons.core.exceptions.UniqueFieldValuesAlreadyExistsException;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ApiErrorResponse;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ResponseUtil;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse<Void>> handleException(
            HttpServletRequest request, Exception ex) {
        List<ValidationField> errors = Arrays.asList(new ValidationField("Erro", ex.getMessage()));

        ApiErrorResponse<Void> response =
                ResponseUtil.error(errors, "Ocorreu um erro", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse<Void>> handleValidationExceptions(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<ValidationField> validationFields = new ArrayList<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String fieldMessage = error.getDefaultMessage();
            validationFields.add(new ValidationField(fieldName, fieldMessage));
        }

        ApiErrorResponse<Void> response =
                ResponseUtil.error(
                        validationFields,
                        "Há campos que foram preenchidos incorretamente",
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueFieldValuesAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse<Void>> handleUniqueFieldValuesAlreadyExistsException(
            UniqueFieldValuesAlreadyExistsException exception, HttpServletRequest request) {

        ApiErrorResponse<Void> response =
                ResponseUtil.error(
                        exception.getExistingFieldValues(),
                        exception.getMessage(),
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiErrorResponse<Void>> handleInvalidTokenException(
            HttpServletRequest request, Exception ex) {
        List<ValidationField> errors = Arrays.asList(new ValidationField("Erro", ex.getMessage()));

        ApiErrorResponse<Void> response =
                ResponseUtil.error(errors, "Token Inválido", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    public ResponseEntity<ApiErrorResponse<Void>> handleUsernameOrPasswordInvalidException(
            HttpServletRequest request, UsernameOrPasswordInvalidException ex) {

        ApiErrorResponse<Void> response =
                ResponseUtil.error(
                        new ValidationField("Erro", ex.getMessage()),
                        "Usuário e/ou senha incorretos",
                        request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
