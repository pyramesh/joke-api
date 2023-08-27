package com.ramesh.joke.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Holds joke's blacklistFlags
 */
public enum Flag {
    EXPLICIT("explicit"),
    NSFW("nsfw"),
    POLITICAL("political"),
    RACIST("racist"),
    RELIGIOUS("religious"),
    SEXIST("sexist");

    private final String name;

    Flag(final String name) {
        this.name = name;
    }

    @JsonValue
    public String getFlags() {
        return name;
    }
}
