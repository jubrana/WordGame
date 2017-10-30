package org.word.game.test;

import org.junit.Before;
import org.junit.Test;
import org.word.game.vocabulary.Vocabulary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VocabularyTest {


    private Vocabulary vocabulary;

    @Before
    public void init() {

        if(vocabulary == null) {

            vocabulary = new Vocabulary();
        }
    }


    @Test
    public void shouldAddInVocabulayIfWordDoesntExist(){

        List<String> words = getSomeWords();

        words.stream().forEach(word -> assertTrue("Add Word - " + word, this.vocabulary.add(word)) );

        words.stream().forEach(word -> assertFalse("Word Exists", this.vocabulary.add(word)) );

    }

    @Test
    public void shouldReturnNodeWithCorrectIndices(){

                    // m   o   r   n   a  r
        byte[] word = {12, 14, 17, 13, 0, 17};

        Vocabulary node = this.getFilledVocabulary().getNode(word);

        assertNotNull(node);

        for (int i = 0; i < word.length; i++) {

            byte[] ind = new byte[word.length -i];

            for(int j = 0; j < ind.length; j++) {

                ind[j] = word[j];
            }

            node = this.getFilledVocabulary().getNode(ind);
            assertNotNull(node);
        }

        word = new byte[]{12, 14, 17, 1, 1, 99};
        node = this.getFilledVocabulary().getNode(word);

        assertNull("No word should be found", node);

    }

    @Test
    public void wordShouldBePrefix(){

        // m   o   r   n   a  r
        byte[] word = {12, 14, 17, 13, 0, 17};

        Vocabulary vocabulary = this.getFilledVocabulary();

        assertTrue("Word should be prefix", vocabulary.isPrefix(word));

    }

    @Test
    public void shouldBeWord(){

        // m   o   r   n   a  r
        byte[] word = {12, 14, 17, 13, 0, 17};

        Vocabulary vocabulary = this.getFilledVocabulary();

        assertTrue("Should be word", vocabulary.contains(word));

    }
    
    
    private List<String> getSomeWords() {

        List<String> words = new ArrayList<>();
        
        words.add("mornar");
        words.add("mornarima");
        words.add("mornarička");
        words.add("mornari");
        words.add("mornaru");
        words.add("mornariti");
        words.add("mislav");
        words.add("mimoza");

        words.add("žariti");
        words.add("žal");

        words.add("kormoran");
        words.add("kormilo");
        words.add("krma");
        words.add("kanta");

        return words;
    }

    private Vocabulary getFilledVocabulary() {

        Vocabulary vocabulary = new Vocabulary();

        getSomeWords().stream().forEach(word -> vocabulary.add(word));

        return vocabulary;
    }

}
