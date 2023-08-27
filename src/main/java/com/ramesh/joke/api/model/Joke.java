package com.ramesh.joke.api.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holds Joke's data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Joke {

    private Category category;
    private Type type;
    private String joke;
    private HashMap<Flag, Boolean> flags;
    private boolean safe;
    private int id;
    private Language lang;

}
