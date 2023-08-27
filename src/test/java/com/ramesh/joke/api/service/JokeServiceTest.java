package com.ramesh.joke.api.service;

import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_500_MESSAGE;
import static com.ramesh.joke.api.constant.ErrorMessages.NO_SAFE_JOKES_FOUND;
import static com.ramesh.joke.api.helper.TestDataHelper.createJoke;
import static com.ramesh.joke.api.helper.TestDataHelper.createJokes;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ramesh.joke.api.exceptions.JokeApiServiceException;
import com.ramesh.joke.api.exceptions.JokeNotFoundException;
import com.ramesh.joke.api.external.ExternalJokeAPIService;
import com.ramesh.joke.api.model.Joke;
import com.ramesh.joke.api.model.Type;
import com.ramesh.joke.api.response.GetJokeResponse;
import com.ramesh.joke.api.response.JokeResponse;

@ExtendWith(MockitoExtension.class)
public class JokeServiceTest {

    @InjectMocks
    private JokeService jokeService;

    @Mock
    private ExternalJokeAPIService externalJokeApiService;

    @Test
    void testGetJokes_ShortestJoke() {
        // Give
        final List<Joke> jokes = createJokes();
        final JokeResponse response = JokeResponse.builder()
                .error(false)
                .jokes(jokes)
                .amount(5)
                .build();
        when(externalJokeApiService.callExternalService()).thenReturn(response);

        // When
        final GetJokeResponse getJokeResponse = jokeService.getJokes();

        // Then
        Assertions.assertThat(getJokeResponse.getId()).isEqualTo(2);
        Assertions.assertThat(getJokeResponse.getRandomJoke()).isEqualTo("I have a joke about trickle down economics, but 99% of you will never get it.");

    }

    @Test
    void testGetJokes_WhenNoJokesFoundFromExternalService() {
        // Give
        final JokeResponse response = JokeResponse.builder()
                .error(true)
                .message(NO_SAFE_JOKES_FOUND)
                .internalError(false)
                .jokes(Collections.emptyList())
                .build();

        when(externalJokeApiService.callExternalService()).thenReturn(response);

        // When
        Assertions.assertThatThrownBy(() -> jokeService.getJokes())
                .isInstanceOf(JokeNotFoundException.class)
                .hasMessage(NO_SAFE_JOKES_FOUND);
    }

    @Test
    void testGetJokes_WhenNoSafeJokes() {
        // Give
        final Joke joke1 = createJoke(1, Type.SINGLE, false, false, false, "joke1.");
        final Joke joke2 = createJoke(2, Type.TWOPART, false, false, false, "joke2.");

        final JokeResponse response = JokeResponse.builder()
                .error(false)
                .jokes(Arrays.asList(joke1, joke2))
                .amount(2)
                .build();
        when(externalJokeApiService.callExternalService()).thenReturn(response);

        // When
        // Then
        Assertions.assertThatThrownBy(() -> jokeService.getJokes())
                .isInstanceOf(JokeNotFoundException.class)
                .hasMessage(NO_SAFE_JOKES_FOUND);
    }

    @Test
    void testGetJokes_WhenSafeJokesFoundButNotSexiestOrExplicit() {
        // Give
        final Joke joke1 = createJoke(1, Type.SINGLE, true, true, true, "joke1.");
        final Joke joke2 = createJoke(2, Type.SINGLE, true, true, true, "joke2.");

        final JokeResponse response = JokeResponse.builder()
                .error(false)
                .jokes(Arrays.asList(joke1, joke2))
                .amount(2)
                .build();
        when(externalJokeApiService.callExternalService()).thenReturn(response);

        // When
        //Then
       Assertions.assertThatThrownBy(() -> jokeService.getJokes())
               .isInstanceOf(JokeNotFoundException.class)
               .hasMessage(NO_SAFE_JOKES_FOUND);

    }

    @Test
    void testGetJokes_WhenSomeInternalError_JokeAPI() {
        // Give

        when(externalJokeApiService.callExternalService()).thenThrow(new JokeApiServiceException(JOKE_API_RESPONSE_STATUS_500_MESSAGE));

        // When
        Assertions.assertThatThrownBy(() -> jokeService.getJokes())
                .isInstanceOf(JokeApiServiceException.class)
                .hasMessage(JOKE_API_RESPONSE_STATUS_500_MESSAGE);
    }
}
