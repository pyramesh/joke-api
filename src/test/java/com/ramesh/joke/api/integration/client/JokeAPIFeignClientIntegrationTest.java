package com.ramesh.joke.api.integration.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.ramesh.joke.api.JokeApiApplication;
import com.ramesh.joke.api.client.JokeApiFeignClient;
import com.ramesh.joke.api.exceptions.JokeApiServiceException;
import com.ramesh.joke.api.exceptions.ServiceNotAvailableException;
import com.ramesh.joke.api.helper.TestDataHelper;
import com.ramesh.joke.api.response.JokeResponse;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JokeApiApplication.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JokeAPIFeignClientIntegrationTest {

    @Autowired
    private JokeApiFeignClient jokeApiFeignClient;

    private WireMockServer wireMockServer;

    @BeforeAll
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8088);
        configureFor("localhost", 8088);
        wireMockServer.start();

    }

    @AfterAll
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

   @Test
    public void testJokes_whenGetJokeCalled_thenReturnAllJokes() throws JsonProcessingException {
        final JokeResponse jokeResponse = TestDataHelper.createJokeApiServiceResponse();

        stubFor(get(urlEqualTo("/joke/Any?type=single&amount=16"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(new ObjectMapper().writeValueAsString(jokeResponse))));

        final ResponseEntity<JokeResponse> jokes = jokeApiFeignClient.getJokes();
        final JokeResponse response = jokes.getBody();
        assertThat(response.getJokes().size()).isEqualTo(5);
        assertThat(response.getAmount()).isEqualTo(5);
        assertThat(response.isError()).isFalse();
    }

    @Test
    public void givenJokeApiIsNotAvailable_whenGetJokesCalled_thenThrowServiceNotAvailableException() {

        stubFor(get(urlEqualTo("/joke/Any?type=single&amount=16"))
                .willReturn(aResponse().withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));

        assertThrows(ServiceNotAvailableException.class, () -> jokeApiFeignClient.getJokes());
    }

    @Test
    public void givenJokesNotFound_whenGetJokeApiCalled_thenThrowBadRequestException() {
        stubFor(get(urlEqualTo("/joke/Any?type=single&amount=16"))
                .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        assertThrows(JokeApiServiceException.class, () -> jokeApiFeignClient.getJokes());
    }
}
