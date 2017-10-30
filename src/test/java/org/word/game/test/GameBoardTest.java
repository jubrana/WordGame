package org.word.game.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.word.game.board.BoardCell;
import org.word.game.board.GameBoard;
import org.word.game.util.GameUtils;
import org.word.game.vocabulary.Vocabulary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.word.game.Alphabet.LOWERCASE;

public class GameBoardTest {


    private static GameBoard board;
    private static List<BoardCell> boardCellWord;

    @BeforeClass
    public static void setBoardAndWordForTest() {

        char[] letters = { 'ž', 'i', 'u', 'i'
                         , 'k', 't', 'u', 'l'
                         , 'š', 'm', 'i', 'e'
                         , 'o', 'a', 'đ', 'l' };

        board = new GameBoard(4,4,letters);
        board.setMinimumWordLength(GameUtils.getMinimumWordLength());

        // tulum
        boardCellWord = new ArrayList<>();
        boardCellWord.add(new BoardCell(1, 1));
        boardCellWord.add(new BoardCell(0, 2));
        boardCellWord.add(new BoardCell(1, 3));
        boardCellWord.add(new BoardCell(1, 2));
        boardCellWord.add(new BoardCell(2, 1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void objectShouldBeInitializedCorrectly() {

        new GameBoard(5, new char[]{'f', 'l'});
    }


    @Test
    public void shouldReturnWordInByteArray() {

        byte[] word = board.getWordAsIndices(boardCellWord);

        StringBuilder strBu = new StringBuilder();

        for (byte b : word) {

            strBu.append(LOWERCASE.alphabet.charAt(b));
        }

        assertEquals("Should return correct byte array word", strBu.toString(), "tulum");
    }


    @Test
    public void shouldReturnWordAsString() {

        String word = board.getWord(boardCellWord);
        assertEquals("Should return correct word", word, "tulum");
    }


    @Test
    public void shouldReturnValidPossibleMoves() {

        LinkedList<BoardCell> linkedList = new LinkedList<>(boardCellWord);
        List<BoardCell> possibleMoves =  board.getPossibleMoves(linkedList);

        assertTrue("Must have 6 possible moves for given word and given board", possibleMoves.size() == 6);

        possibleMoves.forEach(cell -> assertFalse("Possible moves in already played moves", linkedList.contains(cell)) );

        //6 possible moves
        assertTrue(possibleMoves.contains(new BoardCell(2, 2)));
        assertTrue(possibleMoves.contains(new BoardCell(3, 2)));
        assertTrue(possibleMoves.contains(new BoardCell(3, 1)));
        assertTrue(possibleMoves.contains(new BoardCell(3, 0)));
        assertTrue(possibleMoves.contains(new BoardCell(2, 0)));
        assertTrue(possibleMoves.contains(new BoardCell(1, 0)));

        assertFalse(possibleMoves.contains(new BoardCell(3, 3)));
        assertFalse(possibleMoves.contains(new BoardCell(0, 0)));
    }


    @Test
    public void shoulRecurseOverPossibleMoves() {


        Vocabulary vocabulary = new Vocabulary();
        ArrayList<List<BoardCell>> wordsFound = new ArrayList<>();
        LinkedList<BoardCell> linkedListWord = new LinkedList<>(boardCellWord);
        List<String> testWords = new ArrayList<>();

        testWords.add("tulum");
        testWords.add("tulumi");
        testWords.add("tuluma");
        testWords.add("tule");
        testWords.add("tuli");
        testWords.add("traktor");
        testWords.add("timijan");
        testWords.add("tuzla");
        testWords.add("žestoko");
        testWords.add("ranjivo");

        testWords.forEach(word -> vocabulary.add(word));

        int number_of_moves = board.recurse(vocabulary, linkedListWord, wordsFound);

        assertTrue("Should be 7 moves", number_of_moves == 7);
        assertTrue(wordsFound.size() == 3);

        List<String> wordsFoundStrings = wordsFound
                                    .stream()
                                    .map(boardCellWord -> board.getWord(boardCellWord))
                                    .collect(Collectors.toList());

        wordsFoundStrings.forEach( word -> assertTrue("Word should exist in vocabulary", testWords.contains(word)) );


        assertTrue( wordsFoundStrings.contains("tulumi") );
        assertTrue( wordsFoundStrings.contains("tulum") );
        assertTrue( wordsFoundStrings.contains("tuluma") );

    }

    @Test
    public void shouldFindCorrectWordsFromVocabulary() {

        Vocabulary vocabulary = new Vocabulary();
        ArrayList<List<BoardCell>> wordsFound = new ArrayList<>();
        List<String> testWords = new ArrayList<>();

        testWords.add("tulum");
        testWords.add("tulumi");
        testWords.add("tuluma");
        testWords.add("tule");
        testWords.add("tuli");
        testWords.add("štima");
        testWords.add("štimao");
        testWords.add("štimanje");
        testWords.add("tuzla");
        testWords.add("žitu");
        testWords.add("žito");
        testWords.add("vrganj");
        testWords.add("mili");

        testWords.forEach(word -> vocabulary.add(word));

        int moves = board.findWords(vocabulary, wordsFound);

        //On board, word can be found more than once
        Set<String> wordsFoundStrings = wordsFound
                .stream()
                .map(boardCellWord -> board.getWord(boardCellWord))
                .collect(Collectors.toSet());

        assertTrue("Should found 9 words", wordsFoundStrings.size() == 9);
        wordsFoundStrings.forEach( word -> assertTrue("Word should exist in vocabulary", testWords.contains(word)) );
    }
}
