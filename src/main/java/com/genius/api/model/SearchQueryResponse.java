package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchQueryResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Hit[] hits;

    public Hit[] getHits() {
        return hits;
    }
}
