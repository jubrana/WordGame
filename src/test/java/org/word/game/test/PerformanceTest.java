package org.word.game.test;

import org.junit.Before;
import org.junit.Test;
import org.word.game.board.BoardCell;
import org.word.game.board.GameBoard;
import org.word.game.util.GameUtils;
import org.word.game.vocabulary.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTest {

    private Vocabulary trieVocabulary;

    @Before
    public void loadVocabulary() {

        this.trieVocabulary = GameUtils.getLoadedVocabularyFromFile();
    }

    @Test(timeout = 1000)
    public void shouldPerformBelowOneSecond() {

        long start = System.currentTimeMillis();

        char[] letters = {'ž', 'i', 'u', 'i'
                , 'k', 't', 'u', 'l'
                , 'š', 'm', 'i', 'e'
                , 'o', 'a', 'đ', 'l'};

        int boardDimen = 4;
        GameBoard alphabetBoard = new GameBoard(boardDimen, letters);
        alphabetBoard.setMinimumWordLength(GameUtils.getMinimumWordLength());

        ArrayList<List<BoardCell>> foundWords = new ArrayList<>();
        alphabetBoard.findWords(trieVocabulary, foundWords);

        System.out.println("Time for board " + boardDimen + "x" + boardDimen + ": "   + (System.currentTimeMillis() - start) + "ms");
    }
}
