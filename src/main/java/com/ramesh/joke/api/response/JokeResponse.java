package com.ramesh.joke.api.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramesh.joke.api.model.Joke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Holds external joke api response
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class JokeResponse {
    @JsonProperty("error")
    private boolean error;

    @JsonProperty("jokes")
    private List<Joke> jokes;

    @JsonProperty("amount")
    private int amount;

    @JsonIgnore
    @JsonProperty("message")
    private String message;

    @JsonIgnore
    @JsonProperty("internalError")
    private boolean internalError;

}
