package com.ramesh.joke.api.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ramesh.joke.api.client.JokeApiFeignClient;
import com.ramesh.joke.api.exceptions.JokeApiServiceException;
import com.ramesh.joke.api.exceptions.JokeNotFoundException;
import com.ramesh.joke.api.response.JokeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Holds all external service calls
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ExternalJokeAPIService {

    private final JokeApiFeignClient jokeApiFeignClient;

    public JokeResponse callExternalService() {
        final ResponseEntity<JokeResponse> jokeResponseResponseEntity = jokeApiFeignClient.getJokes();
        final JokeResponse jokeResponse = jokeResponseResponseEntity.getBody();
        if (jokeResponse != null && jokeResponse.isError()) {
            if (!jokeResponse.isInternalError()) {
                throw new JokeNotFoundException(jokeResponse.getMessage());
            }
            throw new JokeApiServiceException(jokeResponse.getMessage());
        }
        return jokeResponse;
    }
}
