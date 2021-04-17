package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeniusArtistSvcResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Metadata meta;

    @JsonProperty("response")
    private ArtistResponse response;

    public Metadata getMeta() {
        return meta;
    }

    public ArtistResponse getResponse() {
        return response;
    }
}