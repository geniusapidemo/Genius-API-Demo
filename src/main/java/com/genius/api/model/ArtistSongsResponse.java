package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSongsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Song[] songs;

    public Song[] getSongs() {
        return songs;
    }
}
