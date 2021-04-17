package com.genius.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Exception handler class
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handle(Exception e, WebRequest request) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage());
        return new ResponseEntity<>(mapper.writeValueAsString(errorResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
