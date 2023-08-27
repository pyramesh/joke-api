package com.ramesh.joke.api.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ramesh.joke.api.model.Flag;
import com.ramesh.joke.api.model.Joke;
import com.ramesh.joke.api.model.Language;
import com.ramesh.joke.api.model.Type;
import com.ramesh.joke.api.response.JokeResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Fall back implementation for the external joke api service in case of service unavailability
 */
@Component
@Slf4j
public class JokeApiFeignClientFallBackFactory implements FallbackFactory<JokeApiFeignClient> {

    @Override
    public JokeApiFeignClient create(final Throwable cause) {
        log.error("An exception occurred when calling the JokeApiFeignClient", cause);
        return () -> {
            log.info("[Fallback] getJokes");
            return prepareDummyJokeResponse();
        };
    }

    private static ResponseEntity<JokeResponse> prepareDummyJokeResponse() {
        log.info("preparing default response");
        final HashMap<Flag, Boolean> flags = new HashMap<>();
        flags.put(Flag.EXPLICIT, false);
        flags.put(Flag.SEXIST, false);
        flags.put(Flag.NSFW, true);
        flags.put(Flag.RACIST, true);
        flags.put(Flag.POLITICAL, true);

        final JokeResponse jokeResponse = JokeResponse.builder()
                .error(false)
                .jokes(Arrays.asList(Joke.builder()
                        .id(1)
                        .joke("default joke")
                        .type(Type.SINGLE)
                        .safe(true)
                        .flags(flags)
                        .lang(Language.EN)
                        .build()))
                .amount(1)
                .build();
        return ResponseEntity.of(Optional.of(jokeResponse));
    }
}
