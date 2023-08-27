package com.ramesh.joke.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramesh.joke.api.response.GetJokeResponse;
import com.ramesh.joke.api.service.JokeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller to get joke
 */
@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
@Slf4j
public class JokeController {

    private final JokeService jokeService;

    @Operation(summary = "get jokes", description = "", tags = { "Get Jokes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Joke is retrieved",
                    content = @Content(schema = @Schema(implementation = GetJokeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Joke is not found"),
    })
    @GetMapping
    public ResponseEntity<GetJokeResponse> getJokes() {
        log.info("start JokeController :: getJokes ");
        return ResponseEntity.ok(jokeService.getJokes());
    }

}
