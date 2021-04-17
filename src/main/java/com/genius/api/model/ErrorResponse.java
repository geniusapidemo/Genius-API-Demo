package com.genius.api.model;

import java.io.Serializable;
import java.util.Date;

public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date timestamp;
    private String message;

    public ErrorResponse(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}