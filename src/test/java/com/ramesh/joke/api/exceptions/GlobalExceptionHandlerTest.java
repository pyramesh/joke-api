package com.ramesh.joke.api.exceptions;

import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_GENERIC_RESPONSE;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_500_MESSAGE;
import static com.ramesh.joke.api.constant.ErrorMessages.NO_SAFE_JOKES_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandlers;

    @Test
    void testHandleServiceNotAvailableException(){
        final ServiceNotAvailableException exception = mock(ServiceNotAvailableException.class);
        when(exception.getMessage()).thenReturn(JOKE_API_RESPONSE_STATUS_500_MESSAGE);

        final ResponseEntity<ErrorResponse> errorObject = exceptionHandlers.handleServiceNotAvailableException(exception);

        assertThat(errorObject).isNotNull();
        assertThat(errorObject.getStatusCode().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(errorObject.getBody()).isNotNull();
        assertThat(errorObject.getBody().getErrorCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(errorObject.getBody().getErrorDescription()).isEqualTo(JOKE_API_RESPONSE_STATUS_500_MESSAGE);

    }
    @Test
    void testHandleJokeNotFoundException(){
        final ServiceNotAvailableException exception = mock(ServiceNotAvailableException.class);
        when(exception.getMessage()).thenReturn(NO_SAFE_JOKES_FOUND);

        final ResponseEntity<ErrorResponse> errorObject = exceptionHandlers.handleJokeNotFoundException(exception);

        assertThat(errorObject).isNotNull();
        assertThat(errorObject.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorObject.getBody()).isNotNull();
        assertThat(errorObject.getBody().getErrorCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorObject.getBody().getErrorDescription()).isEqualTo(NO_SAFE_JOKES_FOUND);
    }

    @Test
    void testHandleException(){
        final ServiceNotAvailableException exception = mock(ServiceNotAvailableException.class);
        when(exception.getMessage()).thenReturn(JOKE_API_GENERIC_RESPONSE);

        final ResponseEntity<ErrorResponse> errorObject = exceptionHandlers.handleException(exception);

        assertThat(errorObject).isNotNull();
        assertThat(errorObject.getStatusCode().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(errorObject.getBody()).isNotNull();
        assertThat(errorObject.getBody().getErrorCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(errorObject.getBody().getErrorDescription()).isEqualTo(JOKE_API_GENERIC_RESPONSE);
    }
}
