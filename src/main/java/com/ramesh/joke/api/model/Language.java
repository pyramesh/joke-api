package com.ramesh.joke.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Holds Joke api supported languages
 */
public enum Language {

    /** Czech */
    CS("cs"),

    /** German */
    DE("de"),

    /** English */
    EN("en"),

    /** Spanish */
    ES("es"),

    /** French */
    FR("fr"),

    /** Portuguese */
    PT("pt");

    private final String name;

    Language(final String name) {
        this.name = name;
    }

    @JsonValue
    public String getLang() {
        return name;
    }
}
