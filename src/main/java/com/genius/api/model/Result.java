package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("primary_artist")
    private PrimaryArtist primaryArtist;

    public PrimaryArtist getPrimaryArtist() {
        return primaryArtist;
    }
}
