package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Game implements ChessGame {
    private ChessBoard board;
    private TeamColor currTurn;
    private boolean isOver;

    public Game(Board board, TeamColor currTurn, boolean isOver) {
        this.board = board;
        this.currTurn = currTurn;
        this.isOver = isOver;
    }

    public Game() {
        this.board = new Board();
        this.board.resetBoard();
        currTurn = TeamColor.WHITE;
    }

    @Override
    public TeamColor getTeamTurn() {
        return currTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        currTurn = team;
    }
    public boolean getIsOver() {
        return this.isOver;
    }
    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessPiece piece = board.getPiece(startPosition);
        TeamColor currColor = piece.getTeamColor();
        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        for (ChessMove move : moves) {
            ChessPiece tempPiece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getEndPosition(), piece);
            board.addPiece(move.getStartPosition(), null);
            if (!isInCheck(currColor)) validMoves.add(move);
            board.addPiece(move.getStartPosition(), piece);
            board.addPiece(move.getEndPosition(), tempPiece);
        }
        return validMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        // get the piece at the startPosition
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (!moves.contains(move) || piece.getTeamColor() != currTurn) {
            throw new InvalidMoveException();
        }
        // remove the piece at its original position
        board.addPiece(move.getStartPosition(), null);
        // check for promotion
        if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) piece = new Queen(currTurn);
        else if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) piece = new Rook(currTurn);
        else if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) piece = new Knight(currTurn);
        else if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) piece = new Bishop(currTurn);
        // put the piece in its new position
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
                    if (piece.getTeamColor() != teamColor) {
                        Collection<ChessMove> moves = piece.pieceMoves(board, position);
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
        // can't be in checkmate if current team is not in check
        if (!isInCheck(teamColor)) return false;
        // go through each position
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition position = new Position(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    // find the right king
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        return validMoves(position).isEmpty();
                    }
                }
            }
        }
        return false;
    }

    @Override
    // needs: check and validMoves
    public boolean isInStalemate(TeamColor teamColor) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return board.equals(game.board) && currTurn == game.currTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, currTurn);
    }
}
