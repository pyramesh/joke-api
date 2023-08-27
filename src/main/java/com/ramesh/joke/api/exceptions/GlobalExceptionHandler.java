package com.ramesh.joke.api.exceptions;

import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_GENERIC_RESPONSE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception handler class for the application
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle Service not available exception
     *
     * @param ex
     *            exception
     * @return response entity containing status code and error message
     */
    @ExceptionHandler(value = { ServiceNotAvailableException.class })
    public ResponseEntity<ErrorResponse> handleServiceNotAvailableException(final Throwable ex) {
        log.error("handleServiceNotAvailableException: ={}", ex);
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR.value())
                .errorDescription(ex.getMessage()).build(), INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle Joke not found exception
     *
     * @param ex
     *            exception {@link JokeNotFoundException} {@link JokeNotFoundException}
     * @return response entity containing status code and error message
     */
    @ExceptionHandler(value = { JokeNotFoundException.class, JokeApiServiceException.class })
    public ResponseEntity<ErrorResponse> handleJokeNotFoundException(final Throwable ex) {
        log.error("handleJokeNotFoundException: ={}", ex);
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(NOT_FOUND.value())
                .errorDescription(ex.getMessage()).build(), NOT_FOUND);
    }

    /**
     * handle all generic exceptions.
     *
     * @param ex
     *            exception {@link Exception}
     * @return response entity containing status code and error message
     */
    @ExceptionHandler({ FeignException.class, Exception.class })
    public ResponseEntity<ErrorResponse> handleException(final Exception ex) {
        log.error("handleException: ={}", ex);
        final ErrorResponse error = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), JOKE_API_GENERIC_RESPONSE);
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }
}
