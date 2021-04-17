package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit implements Serializable {

    private Result result;

    public Result getResult() {
        return result;
    }
}
