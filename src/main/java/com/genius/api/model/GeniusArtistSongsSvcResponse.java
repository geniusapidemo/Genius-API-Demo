package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeniusArtistSongsSvcResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Metadata meta;

    @JsonProperty("response")
    private ArtistSongsResponse response;

    public Metadata getMeta() {
        return meta;
    }

    public ArtistSongsResponse getResponse() {
        return response;
    }
}
