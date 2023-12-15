package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pawn implements ChessPiece {

    private final ChessGame.TeamColor teamColor;
    private final PieceType pieceType;

    public Pawn(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
        this.pieceType = PieceType.PAWN;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves = new HashSet<>();

        // white pawns
        if (teamColor == ChessGame.TeamColor.WHITE) {
            // double move
            if (isValidSquare(myPosition.getRow() + 2, myPosition.getColumn()) && myPosition.getRow() == 2) {
                ChessPosition moveOne = new Position(myPosition.getRow() + 1, myPosition.getColumn());
                ChessPosition moveTwo = new Position(myPosition.getRow() + 2, myPosition.getColumn());
                ChessPiece pieceAtTwoForward = board.getPiece(moveTwo);
                ChessPiece pieceAtOneForward = board.getPiece(moveOne);
                if (pieceAtTwoForward == null && pieceAtOneForward == null) {
                    validMoves.add(new Move(myPosition, moveTwo));
                }
            }
            // single move
            if (isValidSquare(myPosition.getRow() + 1, myPosition.getColumn())) {
                ChessPosition moveOne = new Position(myPosition.getRow() + 1, myPosition.getColumn());
                ChessPiece pieceAtOneForward = board.getPiece(moveOne);
                if (pieceAtOneForward == null) {
                    if (moveOne.getRow() == 8) {
                        validMoves.add(new Move(myPosition, moveOne, PieceType.QUEEN));
                        validMoves.add(new Move(myPosition, moveOne, PieceType.ROOK));
                        validMoves.add(new Move(myPosition, moveOne, PieceType.BISHOP));
                        validMoves.add(new Move(myPosition, moveOne, PieceType.KNIGHT));
                    } else {
                        validMoves.add(new Move(myPosition, moveOne));
                    }
                }
            }
            // capture left
            if (isValidSquare(myPosition.getRow() + 1, myPosition.getColumn() - 1)) {
                ChessPosition moveLeft = new Position(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                ChessPiece pieceAtLeft = board.getPiece(moveLeft);
                if (pieceAtLeft != null) {
                    if (pieceAtLeft.getTeamColor() != teamColor) {
                        if (moveLeft.getRow() == 8) {
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.QUEEN));
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.ROOK));
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.BISHOP));
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.KNIGHT));
                        } else {
                            validMoves.add(new Move(myPosition, moveLeft));
                        }
                    }
                }
            }
            // capture right
            if (isValidSquare(myPosition.getRow() + 1, myPosition.getColumn() + 1)) {
                ChessPosition moveRight = new Position(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                ChessPiece pieceAtRight = board.getPiece(moveRight);
                if (pieceAtRight != null) {
                    if (pieceAtRight.getTeamColor() != teamColor) {
                        if (moveRight.getRow() == 8) {
                            validMoves.add(new Move(myPosition, moveRight, PieceType.QUEEN));
                            validMoves.add(new Move(myPosition, moveRight, PieceType.ROOK));
                            validMoves.add(new Move(myPosition, moveRight, PieceType.BISHOP));
                            validMoves.add(new Move(myPosition, moveRight, PieceType.KNIGHT));
                        } else {
                            validMoves.add(new Move(myPosition, moveRight));
                        }
                    }
                }
            }
        }
        // blacks pawns
        else {
            // double move
            if (isValidSquare(myPosition.getRow() - 2, myPosition.getColumn()) && myPosition.getRow() == 7) {
                ChessPosition moveOne = new Position(myPosition.getRow() - 1, myPosition.getColumn());
                ChessPosition moveTwo = new Position(myPosition.getRow() - 2, myPosition.getColumn());
                ChessPiece pieceAtTwoForward = board.getPiece(moveTwo);
                ChessPiece pieceAtOneForward = board.getPiece(moveOne);
                if (pieceAtTwoForward == null && pieceAtOneForward == null) {
                    validMoves.add(new Move(myPosition, moveTwo));
                }
            }
            // single move
            if (isValidSquare(myPosition.getRow() - 1, myPosition.getColumn())) {
                ChessPosition moveOne = new Position(myPosition.getRow() - 1, myPosition.getColumn());
                ChessPiece pieceAtOneForward = board.getPiece(moveOne);
                if (pieceAtOneForward == null) {
                    if (moveOne.getRow() == 1) {
                        validMoves.add(new Move(myPosition, moveOne, PieceType.QUEEN));
                        validMoves.add(new Move(myPosition, moveOne, PieceType.ROOK));
                        validMoves.add(new Move(myPosition, moveOne, PieceType.BISHOP));
                        validMoves.add(new Move(myPosition, moveOne, PieceType.KNIGHT));
                    } else {
                        validMoves.add(new Move(myPosition, moveOne));
                    }
                }
            }
            // capture left
            if (isValidSquare(myPosition.getRow() - 1, myPosition.getColumn() - 1)) {
                ChessPosition moveLeft = new Position(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                ChessPiece pieceAtLeft = board.getPiece(moveLeft);
                if (pieceAtLeft != null) {
                    if (pieceAtLeft.getTeamColor() != teamColor) {
                        if (moveLeft.getRow() == 1) {
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.QUEEN));
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.ROOK));
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.BISHOP));
                            validMoves.add(new Move(myPosition, moveLeft, PieceType.KNIGHT));
                        } else {
                            validMoves.add(new Move(myPosition, moveLeft));
                        }
                    }
                }
            }
            // capture right
            if (isValidSquare(myPosition.getRow() - 1, myPosition.getColumn() + 1)) {
                ChessPosition moveRight = new Position(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                ChessPiece pieceAtRight = board.getPiece(moveRight);
                if (pieceAtRight != null) {
                    if (pieceAtRight.getTeamColor() != teamColor) {
                        if (moveRight.getRow() == 1) {
                            validMoves.add(new Move(myPosition, moveRight, PieceType.QUEEN));
                            validMoves.add(new Move(myPosition, moveRight, PieceType.ROOK));
                            validMoves.add(new Move(myPosition, moveRight, PieceType.BISHOP));
                            validMoves.add(new Move(myPosition, moveRight, PieceType.KNIGHT));
                        } else {
                            validMoves.add(new Move(myPosition, moveRight));
                        }
                    }
                }
            }
        }
        return validMoves;
    }
    private boolean isValidSquare(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return teamColor == pawn.teamColor && pieceType == pawn.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}
