package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class Error {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    private Error() {
        timestamp = LocalDateTime.now();
    }

    public Error(HttpStatus status) {
        this();
        this.status = status;
    }

    public Error(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public Error(HttpStatus status, String message, String debugMessage) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public Error(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
