package com.ramesh.joke.api.config;

import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_GENERIC_RESPONSE;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_400_MESSAGE;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_403_MESSAGE;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_404_MESSAGE;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_503_MESSAGE;

import com.ramesh.joke.api.exceptions.JokeApiServiceException;
import com.ramesh.joke.api.exceptions.ServiceNotAvailableException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * handles feign client exceptions and rethrow some meaningful business exceptions
 */
@Slf4j
public class FeignCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(final String s, final Response response) {
        switch (response.status()) {
            case 400:
                log.error("Error while calling joke API via feign client {} ", response.reason());
                return new JokeApiServiceException(JOKE_API_RESPONSE_STATUS_400_MESSAGE);
            case 404:
                log.error("Error while calling joke API via feign client {} ", response.reason());
                return new JokeApiServiceException(JOKE_API_RESPONSE_STATUS_404_MESSAGE);
            case 403:
                log.error("Error while calling joke API via feign client {} ", response.reason());
                return new JokeApiServiceException(JOKE_API_RESPONSE_STATUS_403_MESSAGE);
            case 500:
                log.error("Error while calling joke API via feign client {} ", response.reason());
                return new ServiceNotAvailableException(response.status(), response.reason());
            case 503:
            case 523:
                log.error("Error while calling joke API via feign client {} ", response.reason());
                return new ServiceNotAvailableException(response.status(), JOKE_API_RESPONSE_STATUS_503_MESSAGE);
            default:
                log.error("Error while calling joke API via feign client {} ", response.reason());
                return new JokeApiServiceException(JOKE_API_GENERIC_RESPONSE);
        }
    }
}
