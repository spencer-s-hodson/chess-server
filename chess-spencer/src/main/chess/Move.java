package chess;

import java.util.Objects;
import java.util.Set;

public class Move implements ChessMove {
    private ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;

    public Move(ChessPosition s, ChessPosition e) { // That way I don't have to put a null everywhere since pieces don't promote most of the time.
        startPosition = s;
        endPosition = e;
        promotionPiece = null;
    }
    public Move(ChessPosition s, ChessPosition e, ChessPiece.PieceType p) { // When a promotion takes place
        startPosition = s;
        endPosition = e;
        promotionPiece = p;
    }

    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }
    public void applyMove() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(startPosition, move.startPosition) && Objects.equals(endPosition, move.endPosition) && promotionPiece == move.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }
}


