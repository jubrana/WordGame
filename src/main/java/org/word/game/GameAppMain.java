package org.word.game;

import org.word.game.board.BoardCell;
import org.word.game.board.GameBoard;

import org.word.game.util.GameUtils;
import org.word.game.vocabulary.Vocabulary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class GameAppMain {
	
	public static void main(String[] args) {


		Vocabulary trieVocabulary = GameUtils.getLoadedVocabularyFromFile();

		if(trieVocabulary == null) {
			System.out.println("Vocabulary not loaded.");
			exitApp();
		}

        System.out.print("Enter board dimension: ");
        Scanner scanner = new Scanner(System.in);
        String dimens = scanner.nextLine();

        System.out.print("Enter board letters: ");
        scanner = new Scanner(System.in);
        String boardLetters = scanner.nextLine();

		long start = System.currentTimeMillis();

		//exit if entered q
        if ("q".equals(dimens)
				|| "q".equals(boardLetters)) {

        	exitApp();
        }

		String lineRegex = GameUtils.getVocabularyLineRegex();

        // Board letters should also conform this regex
		// (no numbers or special characters, only from a - ž)
        if(!boardLetters.matches(lineRegex)) {

			System.out.println("Wrong input format for board letters");
			exitApp();
		}


		int boardDimension = 0;

		try {

			boardDimension = Integer.valueOf(dimens);
		} catch (NumberFormatException ex) {

			System.out.println("Wrong input format for board dimension");
			exitApp();
		}
		

		char[] letters = boardLetters.toCharArray();

		GameBoard alphabetBoard = new GameBoard(boardDimension,letters);
        alphabetBoard.setMinimumWordLength(GameUtils.getMinimumWordLength());

		ArrayList<List<BoardCell>> foundWords = new ArrayList<>();
		int moves = alphabetBoard.findWords(trieVocabulary, foundWords);

		long end = System.currentTimeMillis() - start;

		System.out.println("------------------------- Words -----------------------------");

		Set<String> wordsFoundStrings = foundWords
				.stream()
				.map(boardCellWord -> alphabetBoard.getWord(boardCellWord))
				.collect(Collectors.toSet());

		wordsFoundStrings.forEach(word -> System.out.println(word));

		System.out.println("Completed in " + end + " ms. Found " + wordsFoundStrings.size() + " unique words and "
								+ foundWords.size() + " non-unique words on board. \nMoves: " + moves);


	}

	private static void exitApp() {

		System.out.println("Exit!");
		System.exit(0);
	}
}
