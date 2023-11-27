package chess;

import java.util.Objects;

public class Position implements ChessPosition { // can exist without Move
    private int row; // 1-8, these need to be different somehow. How is the chess board stored, is it a grid?
    private int column; // 1-8

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
