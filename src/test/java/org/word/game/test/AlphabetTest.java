package org.word.game.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import static org.word.game.Alphabet.LOWERCASE;

public class AlphabetTest {


    @Test
    public void shouldHaveExactCharactersOrderInAlphabet() {

        for(int i = 0; i < LOWERCASE.alphabet.length(); i++) {

            char c = LOWERCASE.alphabet.charAt(i);

            assertEquals("Index of char " + c, i, LOWERCASE.getIndex(c));
        }

    }
}
