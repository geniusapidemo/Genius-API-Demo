package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Artist artist;

    public Artist getArtist() {
        return artist;
    }
}