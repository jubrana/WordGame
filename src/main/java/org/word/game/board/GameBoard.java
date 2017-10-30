package org.word.game.board;

import org.word.game.vocabulary.Vocabulary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.word.game.Alphabet.LOWERCASE;


public class GameBoard {

	byte[][] board;

    private int rows;
    private int columns;

    private int minimumWordLength;

	public GameBoard(int r, int c, char[] letters) {

        rows = r;
        columns = c;

        if (letters.length != r * c) {
            throw new IllegalArgumentException(
                    "Wrong number of letters for board");
        }

		board = new byte[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				int k = i * c + j;
				board[i][j] = LOWERCASE.getIndex(letters[k]);
			}
		}
	}


    public GameBoard(int dimension, char[] letters) {

	    this(dimension, dimension, letters);
    }


    public int recurse(Vocabulary vocabulary,
                          LinkedList<BoardCell> usedCells, ArrayList<List<BoardCell>> words) {

        int r = 1;
        byte[] word = getWordAsIndices(usedCells);


        if ((word.length >= this.minimumWordLength) && vocabulary.contains(word)) {

            words.add(new ArrayList<>(usedCells));
        }
        if (vocabulary.isPrefix(word)) {

            // Search for moves
            List<BoardCell> moves = getPossibleMoves(usedCells);
            for (BoardCell move : moves) {
                usedCells.add(move);
                r += recurse(vocabulary, usedCells, words);
                usedCells.pollLast();
            }
        }
        return r;
    }

    public List<BoardCell> getPossibleMoves(
            LinkedList<BoardCell> usedCells) {

        // Possible moves are up/down/left/right/ur/ul/dr/dl from last cell
        LinkedList<BoardCell> moves = new LinkedList<>();
        BoardCell last = usedCells.peekLast();
        boolean u, d, r, l;

        u = last.row - 1 >= 0;
        d = last.row + 1 < rows;
        r = last.col + 1 < columns;
        l = last.col - 1 >= 0;

        if (u) { // up
            moves.add(new BoardCell(last.row - 1, last.col));
        }
        if (d) { // down
            moves.add(new BoardCell(last.row + 1, last.col));
        }

        if (r) { // right
            moves.add(new BoardCell(last.row, last.col + 1));
        }
        if (l) { // left
            moves.add(new BoardCell(last.row, last.col - 1));
        }
        if (u && l) { // up and left
            moves.add(new BoardCell(last.row - 1, last.col - 1));
        }
        if (u && r) { // up and right
            moves.add(new BoardCell(last.row - 1, last.col + 1));
        }
        if (d && l) { // down and left
            moves.add(new BoardCell(last.row + 1, last.col - 1));
        }
        if (d && r) { // down and right
            moves.add(new BoardCell(last.row + 1, last.col + 1));
        }
        // Don't go back
        moves.removeAll(usedCells);
        return moves;
    }


    public int findWords(Vocabulary vocabulary,
                               ArrayList<List<BoardCell>> words) {
        LinkedList<BoardCell> usedCells = new LinkedList<>();
        int moves = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                BoardCell cell = new BoardCell(i, j);
                usedCells.add(cell);
                moves += recurse(vocabulary, usedCells, words);
                usedCells.pollLast();
            }
        }
        return moves;
    }


	public byte[] getWordAsIndices(List<BoardCell> list) {

		byte[] r = new byte[list.size()];

		for (int i = 0; i < list.size(); i++) {

			BoardCell cell = list.get(i);
			r[i] = board[cell.row][cell.col];
		}

		return r;
	}


	private char getCharAt(int row, int col) {

		return LOWERCASE.alphabet.charAt(board[row][col]);
	}


    public String getWord(List<BoardCell> usedCells) {

        StringBuffer sb = new StringBuffer(usedCells.size());

        for (BoardCell cell : usedCells) {
            sb.append(getCharAt(cell.row, cell.col));
        }

        return sb.toString();
    }

    public void setMinimumWordLength(int minimumWordLength) {
        this.minimumWordLength = minimumWordLength;
    }
}
