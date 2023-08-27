package com.ramesh.joke.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ramesh.joke.api.response.GetJokeResponse;
import com.ramesh.joke.api.service.JokeService;

@ExtendWith(MockitoExtension.class)
public class JokeControllerTest {

    @InjectMocks
    JokeController jokeController;

    @Mock
    JokeService jokeService;


    @Test
    void testGetJokes_Successful() {
        //Given
        final GetJokeResponse response = GetJokeResponse.builder()
                .id(1)
                .randomJoke("randomJoke")
                .build();
        when(jokeService.getJokes()).thenReturn(response);

        //When
        final ResponseEntity<GetJokeResponse> responseEntity = jokeController.getJokes();

        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(1);
        assertThat(responseEntity.getBody().getRandomJoke()).isEqualTo("randomJoke");
    }

    @Test
    void testGetJokes_WhenJokeNotFound() {
        //Given
        final GetJokeResponse response = null;
        when(jokeService.getJokes()).thenReturn(response);

        //When
        final ResponseEntity<GetJokeResponse> responseEntity = jokeController.getJokes();

        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNull();
    }

}
