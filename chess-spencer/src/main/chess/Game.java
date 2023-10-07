package chess;

import java.util.Collection;

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
        return null;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException { // updates the position based off of the move it gives, checks to see if the move is valid

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    } // uses makeMove to see if it would kill the king?

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    } // get the set of moves for the king and see if all of those including his starting posiotion are inCheckmate

    @Override
    public boolean isInStalemate(TeamColor teamColor) { // go through every positon on the board, and if validMoves.size() == 0 at every positon, then it doesn't work
        return false;
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
