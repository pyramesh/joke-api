package com.ramesh.joke.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ramesh.joke.api.config.FeignClientConfiguration;
import com.ramesh.joke.api.response.JokeResponse;

/**
 * Client for external jokeAPI service
 */
@FeignClient(value = "${joke.api.feign.config.name}", url = "${joke.api.feign.config.url}", configuration = FeignClientConfiguration.class,
        fallback = JokeApiFeignClientFallBackFactory.class)
public interface JokeApiFeignClient {

    /**
     * Get jokes from external Joke API
     * 
     * @return jokes response
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<JokeResponse> getJokes();
}
