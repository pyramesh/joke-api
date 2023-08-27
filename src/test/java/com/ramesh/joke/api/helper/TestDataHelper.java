package com.ramesh.joke.api.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.ramesh.joke.api.model.Flag;
import com.ramesh.joke.api.model.Joke;
import com.ramesh.joke.api.model.Language;
import com.ramesh.joke.api.model.Type;
import com.ramesh.joke.api.response.JokeResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to create test data
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataHelper {

    public static JokeResponse createJokeApiServiceResponse(){
        return JokeResponse.builder()
                .error(false)
                .jokes(createJokes())
                .amount(5)
                .build();
    }

    public static List<Joke> createJokes() {
        final Joke joke1 = createJoke(1, Type.SINGLE,true, false, false, "Java and C were telling jokes. It was C's turn, so he writes something on the wall, points to it and says \"Do you get the reference?\" But Java didn't.");
        final Joke joke2 = createJoke(2,Type.SINGLE,true, false, false, "I have a joke about trickle down economics, but 99% of you will never get it.");
        final Joke joke3 = createJoke(3,Type.SINGLE,true, false, false, "Java is like Alzheimer's, it starts off slow, but eventually, your memory is gone.");
        final Joke joke4 = createJoke(4,Type.SINGLE,true, false, false, "A programmer puts two glasses on his bedside table before going to sleep.\\n\" +\n" +
                "                        \"A full one, in case he gets thirsty, and an empty one, in case he doesn't.");
        final Joke joke5 = createJoke(5,Type.SINGLE,true, true, false, "A byte walks into a bar looking miserable.\\n\" +\n" +
                "                        \"The bartender asks it: \\\"What's wrong buddy?\\\"\\n\" +\n" +
                "                        \"\\\"Parity error.\\\" it replies. \\n\" +\n" +
                "                        \"\\\"Ah that makes sense, I thought you looked a bit off.");

        return Arrays.asList(joke1, joke2,joke3, joke4, joke5);
    }

    public static Joke createJoke(final int id, final Type jokeType, final Boolean isSafe, final Boolean isSexiest, final Boolean isExplicit, final String joke) {
        return Joke.builder()
                .type(jokeType)
                .joke(joke)
                .flags(createFlags(isSexiest, isExplicit))
                .id(id)
                .safe(isSafe)
                .lang(Language.EN)
                .build();
    }

    private static HashMap<Flag, Boolean> createFlags(final Boolean isSexiest, final Boolean isExplicit) {
        final HashMap<Flag, Boolean> flags = new HashMap<>();
        flags.put(Flag.EXPLICIT, isExplicit);
        flags.put(Flag.SEXIST, isSexiest);
        flags.put(Flag.NSFW, true);
        flags.put(Flag.RACIST, true);
        flags.put(Flag.POLITICAL, true);
        return flags;
    }
}
