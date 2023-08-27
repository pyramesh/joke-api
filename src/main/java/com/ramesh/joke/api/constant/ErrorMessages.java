package com.ramesh.joke.api.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Holds all constants related to error messages
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String JOKE_API_RESPONSE_STATUS_400_MESSAGE = "The request you have sent to JokeAPI is formatted incorrectly and cannot be processed";
    public static final String JOKE_API_RESPONSE_STATUS_404_MESSAGE = "The URL you have requested couldn't be found";
    public static final String JOKE_API_RESPONSE_STATUS_403_MESSAGE = "You have been added to the blacklist due to malicious behavior and are not allowed to send requests to JokeAPI anymore";
    public static final String JOKE_API_RESPONSE_STATUS_503_MESSAGE = "JokeAPI is not available.";
    public static final String JOKE_API_RESPONSE_STATUS_500_MESSAGE = "There was a internal error within JokeAPI";
    public static final String JOKE_API_GENERIC_RESPONSE = "Something went wrong while calling jokeAPI service.";
    public static final String NO_SAFE_JOKES_FOUND = "No safe jokes found.";
}
