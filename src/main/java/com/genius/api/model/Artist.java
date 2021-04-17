package com.genius.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    @JsonProperty("followers_count")
    private long followersCount;

    public int getId() {
        return id;
    }

    public long getFollowersCount() {
        return followersCount;
    }
}
