package com.ramesh.joke.api.exceptions;

/**
 * Exception class for joke not found
 */
public class JokeApiServiceException extends RuntimeException {

    /**
     * Create an exception with a custom message
     * 
     * @param message
     *            the message to be displayed
     */
    public JokeApiServiceException(final String message) {
        super(message);
    }

}
