package com.ramesh.joke.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Holds Joke Types
 */
public enum Type {

    SINGLE("single"),
    TWOPART("twopart");

    private final String name;

    Type(final String name) {
        this.name = name;
    }

    @JsonValue
    public String getType() {
        return name;
    }

}
