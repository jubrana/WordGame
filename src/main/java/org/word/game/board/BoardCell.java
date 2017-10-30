package org.word.game.board;

public class BoardCell {

    int row;
    int col;

    public BoardCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof BoardCell) {

            BoardCell j = (BoardCell) obj;
            return j.row == row && j.col == col;
        }
        return false;
    }

    @Override
    public int hashCode() {

        return row * 50 + col;
    }
}
