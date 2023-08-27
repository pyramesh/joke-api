package com.ramesh.joke.api.integration.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_400_MESSAGE;
import static com.ramesh.joke.api.constant.ErrorMessages.JOKE_API_RESPONSE_STATUS_503_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.ramesh.joke.api.JokeApiApplication;
import com.ramesh.joke.api.exceptions.ErrorResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = JokeApiApplication.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class JokeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private WireMockServer wireMockServer;

    @BeforeAll
    void startWireMockServer() {
        wireMockServer = new WireMockServer(8088);
        configureFor("localhost", 8088);
        wireMockServer.start();
    }

    @AfterAll
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void givenJokeApiIsNotAvailable_whenGetJokesCalled_ThenReturnInternalServerError() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/joke/Any?type=single&amount=16"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .withStatusMessage(JOKE_API_RESPONSE_STATUS_503_MESSAGE)));

        final ErrorResponse expectedError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                JOKE_API_RESPONSE_STATUS_503_MESSAGE);

        final MvcResult result = mockMvc.perform(get("/jokes"))
                .andExpect(status().isInternalServerError()).andReturn();

        final ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals(expectedError.getErrorCode(), errorResponse.getErrorCode());
        assertEquals(expectedError.getErrorDescription(), errorResponse.getErrorDescription());
    }

    @Test
    public void givenJokeIsNotFound_whenGetJokeApiCalled_ThenReturnInternalServerError() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/joke/Any?type=single&amount=16"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withStatusMessage(JOKE_API_RESPONSE_STATUS_400_MESSAGE)));

        final ErrorResponse expectedError = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                JOKE_API_RESPONSE_STATUS_400_MESSAGE);

        final MvcResult result = mockMvc.perform(get("/jokes"))
                .andExpect(status().isNotFound()).andReturn();

        final ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals(expectedError.getErrorCode(), errorResponse.getErrorCode());
    }
}
