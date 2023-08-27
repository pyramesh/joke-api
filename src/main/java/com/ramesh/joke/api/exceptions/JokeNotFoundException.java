package com.ramesh.joke.api.exceptions;

/**
 * Exception class for joke not found
 */
public class JokeNotFoundException extends RuntimeException {

    /**
     * Create an exception with a custom message
     * 
     * @param message
     *            the message to be displayed
     */
    public JokeNotFoundException(final String message) {
        super(message);
    }

}
