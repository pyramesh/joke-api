package com.ramesh.joke.api.exceptions;

import feign.FeignException;

/**
 * Exception class for JOKE API service not available
 */
public class ServiceNotAvailableException extends FeignException {

    /**
     * Create an exception with a custom message
     * 
     * @param message
     *            the message to be displayed
     */
    public ServiceNotAvailableException(final int status, final String message) {
        super(status, message);
    }

}
