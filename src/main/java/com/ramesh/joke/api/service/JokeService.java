package com.ramesh.joke.api.service;

import static java.util.Optional.ofNullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ramesh.joke.api.constant.ErrorMessages;
import com.ramesh.joke.api.exceptions.JokeApiServiceException;
import com.ramesh.joke.api.exceptions.JokeNotFoundException;
import com.ramesh.joke.api.external.ExternalJokeAPIService;
import com.ramesh.joke.api.model.Flag;
import com.ramesh.joke.api.model.Joke;
import com.ramesh.joke.api.response.GetJokeResponse;
import com.ramesh.joke.api.response.JokeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class to call external service and do filter the response
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JokeService {

    private final ExternalJokeAPIService externalJokeApiService;

    /**
     * get Jokes from external service and filter
     *
     * @return response {@link GetJokeResponse}
     */
    public GetJokeResponse getJokes() {
        log.info("start JokeService :: getJokes ");
        final JokeResponse jokeResponse = externalJokeApiService.callExternalService();
        return filterJokes(jokeResponse);
    }

    private static GetJokeResponse filterJokes(final JokeResponse jokeResponse) {
        return ofNullable(jokeResponse)
                .orElseThrow(() -> new JokeApiServiceException(ErrorMessages.JOKE_API_GENERIC_RESPONSE))
                .getJokes()
                .stream()
                .filter(Objects::nonNull)
                .filter(Joke::isSafe)
                .filter(joke -> filterOffensiveJokes(joke.getFlags()))
                .min(Comparator.comparing(joke -> joke.getJoke().length()))
                .map(JokeService::constructResponse)
                .orElseThrow(() -> new JokeNotFoundException(ErrorMessages.NO_SAFE_JOKES_FOUND));
    }

    private static GetJokeResponse constructResponse(final Joke joke) {
        return new GetJokeResponse(joke.getId(), joke.getJoke());
    }

    private static boolean filterOffensiveJokes(final HashMap<Flag, Boolean> flags) {
        return flags.entrySet()
                .stream()
                .anyMatch(entry -> (entry.getKey() == Flag.EXPLICIT && entry.getValue() == Boolean.FALSE) ||
                        (entry.getKey() == Flag.SEXIST && entry.getValue() == Boolean.FALSE));

    }
}
