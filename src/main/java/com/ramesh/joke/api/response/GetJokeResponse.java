package com.ramesh.joke.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Holds GET Api response
 */
@Data
@Builder
@AllArgsConstructor
public class GetJokeResponse {
    private int id;
    private String randomJoke;
}
