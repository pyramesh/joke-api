package com.ramesh.joke.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Holds Joke's Categories
 */
public enum Category {
    ANY("Any"),
    CHRISTMAS("Christmas"),
    DARK("Dark"),
    MISC("Misc"),
    PROGRAMMING("Programming"),
    PUN("Pun"),
    SPOOKY("Spooky");

    private final String name;

    Category(final String name) {
        this.name = name;
    }

    @JsonValue
    public String getCategory() {
        return name;
    }


}
