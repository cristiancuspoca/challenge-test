package com.neoris.challenge.exception;

import com.neoris.challenge.api.v1.resource.dto.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionCustomHandler {

    private ResponseEntity<Object> buildResponseEntity(List<Error> errors, HttpStatus status) {
        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Error error = new Error(httpStatus, ex.getLocalizedMessage());
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }

    @ExceptionHandler(TransactionException.class)
    protected ResponseEntity<Object> handleTransactionException(
            TransactionException ex) {
        HttpStatus httpStatus = HttpStatus.PAYMENT_REQUIRED;
        Error error = new Error(httpStatus, ex.getLocalizedMessage());
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(
            BindException ex) {
        List<Error> errors = new ArrayList<>();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        for (FieldError errorBind : ex.getBindingResult().getFieldErrors()) {
            String bindError = errorBind.getField() + ": " + errorBind.getDefaultMessage();
            Error error = new Error(httpStatus, bindError, ex.getLocalizedMessage());
            errors.add(error);
        }
        for (ObjectError errorBind : ex.getBindingResult().getGlobalErrors()) {
            String bindError = errorBind.getObjectName() + ": " + errorBind.getDefaultMessage();
            Error error = new Error(httpStatus, bindError);
            errors.add(error);
        }
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatchException(
            TypeMismatchException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Error error = new Error(httpStatus, ex.getLocalizedMessage());
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Error error = new Error(httpStatus, ex.getLocalizedMessage());
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<Error> errors = new ArrayList<>();
        for (ConstraintViolation ignored : ex.getConstraintViolations()) {
            String bindError = ignored.getPropertyPath() + " : " + ignored.getMessage();
            Error error = new Error(httpStatus, bindError, ex.getLocalizedMessage());
            errors.add(error);
        }
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(
            Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = new Error(httpStatus, ex.getLocalizedMessage());
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        log.error("Details error: HttpStatus={}, localizedMessage={}", httpStatus, ex.getLocalizedMessage(), ex);
        return buildResponseEntity(errors, httpStatus);
    }
}
