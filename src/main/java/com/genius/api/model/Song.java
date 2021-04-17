package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "api_path", "full_title"})
@ApiModel
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fullTitle;

    private long id;

    private String apiPath;

    public String getFullTitle() {
        return fullTitle;
    }

    public long getId() {
        return id;
    }

    public String getApiPath() {
        return apiPath;
    }

    @JsonProperty(value = "full_title")
    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    @JsonProperty(value = "id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty(value = "api_path")
    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }
}