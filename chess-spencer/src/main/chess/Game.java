package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Game implements ChessGame {
    private ChessBoard board;
    private TeamColor currTurn;
    @Override
    public TeamColor getTeamTurn() {
        return currTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        currTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) { // check to see if all the moves put the King in danger, is this for pieces that are at the given position only?
        /* Figure out what piece is at that position
        * get that piece's moves
        * for each move in moves, makeMove
        * then run isInCheck
        * But here is where I need to check for Checkmate etc.*/
        Collection<ChessMove> validMoves = new HashSet<>();

        ChessPiece piece = board.getPiece(startPosition);
        TeamColor currColor = piece.getTeamColor();
        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        for (ChessMove move : moves) {
            // piece that might get captured
            ChessPiece tempPiece = board.getPiece(move.getEndPosition());

            // temp move
            board.addPiece(move.getEndPosition(), piece);
            board.addPiece(move.getStartPosition(), null);

            // check for check
            if (!isInCheck(currColor)) validMoves.add(move);

            // put back piece that moved
            board.addPiece(move.getStartPosition(), piece);

            // put back piece that might have been captured
            board.addPiece(move.getEndPosition(), tempPiece);
        }
        return validMoves;
    }

    @Override // come back to the invalid move later
    public void makeMove(ChessMove move) throws InvalidMoveException { // updates the position based off of the move it gives, checks to see if the move is valid
        Collection<ChessMove> moves = validMoves(move.getStartPosition());

        // get the piece at the startPosition
        ChessPiece piece = board.getPiece(move.getStartPosition());

        if (!moves.contains(move) || piece.getTeamColor() != currTurn) {
            throw new InvalidMoveException();
        }


//        ChessPiece piece = board.getPiece(move.getStartPosition());

        // remove the piece at it's original position
        board.addPiece(move.getStartPosition(), null);

        // check for promotion
        if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) piece = new Queen(currTurn);
        else if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) piece = new Rook(currTurn);
        else if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) piece = new Knight(currTurn);
        else if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) piece = new Bishop(currTurn);

        // put the piece in it's new position
        board.addPiece(move.getEndPosition(), piece);

        // update whose turn it is
        if (currTurn == TeamColor.WHITE) setTeamTurn(TeamColor.BLACK);
        else setTeamTurn(TeamColor.WHITE);
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition position = new Position(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() != currTurn) {
                        Collection<ChessMove> moves = piece.pieceMoves(board, position); // need to fix this method first
                        for (ChessMove move : moves) {
                            ChessPiece endPiece = board.getPiece(move.getEndPosition());
                            if (endPiece != null) {
                                if (endPiece.getPieceType() == ChessPiece.PieceType.KING && endPiece.getTeamColor() == teamColor) return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        /*
        * Find the king with the right color
        * ifValidMoves is empty and current positon is in check return true
        */

        // can't be in checkmate if current team is not in check
        if (!isInCheck(teamColor)) return false;
        // go through each position
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                // stuff to use
                ChessPosition position = new Position(row, col);
                // doesn't like this right now, check for null
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    // find the right king
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        if (validMoves(position).isEmpty()) return true;
                        else return false;
                    }
                }
            }
        }
        return false;
    }
    // get the set of moves for the king and see if all of those including his starting posiotion are inCheckmate

    @Override
    // needs: check and validMoves
    public boolean isInStalemate(TeamColor teamColor) { // go through every positon on the board, and if validMoves.size() == 0 at every positon, then it doesn't work
        /*
        * go through each position on the board
        * check to see if the position has a piece
        * if it does, see if it's moves are empty
        * if not empty return false
        * return true at the end
        */

        // make sure it's actually their turn
        if (teamColor != currTurn) return false;

        // go through each position
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition position = new Position(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == currTurn) {
                    Collection<ChessMove> moves = validMoves(position); // need to fix this method first
                    if (!moves.isEmpty()) return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
