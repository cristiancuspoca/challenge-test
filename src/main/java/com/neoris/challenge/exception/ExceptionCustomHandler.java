package com.neoris.challenge.exception;

import com.neoris.challenge.api.v1.resource.dto.Error;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionCustomHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(Error error) {
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        Error error = new Error(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return buildResponseEntity(error);
    }

    @ExceptionHandler(TransactionException.class)
    protected ResponseEntity<Object> handleTransactionException(
            TransactionException ex) {
        Error error = new Error(HttpStatus.PAYMENT_REQUIRED, ex.getLocalizedMessage());
        return buildResponseEntity(error);
    }
}
