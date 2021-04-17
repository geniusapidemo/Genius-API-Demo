package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeniusSearchSvcResponse {

    private static final long serialVersionUID = 1L;

    private Metadata meta;

    private SearchQueryResponse response;

    public Metadata getMeta() {
        return meta;
    }

    public SearchQueryResponse getResponse() {
        return response;
    }
}