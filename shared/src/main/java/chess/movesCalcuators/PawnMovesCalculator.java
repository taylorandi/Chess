package chess.movesCalcuators;

import chess.*;

import java.util.HashSet;

public class PawnMovesCalculator {


    public HashSet calculateWhiteMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = ChessGame.TeamColor.WHITE;
        HashSet<ChessMove> moves = new HashSet<>();
            if (myPosition.getRow() == 2) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                if (board.getPiece(position) == null) {
                    var newMove = new ChessMove(myPosition, position, null);
                    moves.add(newMove);
                    position = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn());
                    if (board.getPiece(position) == null) {
                        newMove = new ChessMove(myPosition, position, null);
                        moves.add(newMove);
                    }
                }
            }
            if (myPosition.getRow() + 1 < 8) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                if (board.getPiece(position) == null) {
                    var newMove = new ChessMove(myPosition, position, null);
                    moves.add(newMove);
                }
            }
            if (myPosition.getRow() + 1 == 8) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                if (board.getPiece(position) == null) {
                    promotionMoves(myPosition, position, moves);
                }
            }
            if (myPosition.getRow() + 1 < 8 && myPosition.getColumn() + 1 < 9) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                    var newMove = new ChessMove(myPosition, position, null);
                    moves.add(newMove);
                }
            }
            if (myPosition.getRow() + 1 < 8 && myPosition.getColumn() - 1 > 0) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                    var newMove = new ChessMove(myPosition, position, null);
                    moves.add(newMove);
                }
            }
            if (myPosition.getRow() + 1 == 8 && myPosition.getColumn() + 1 < 9) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                    promotionMoves(myPosition, position, moves);
                }
            }
            if (myPosition.getRow() + 1 == 8 && myPosition.getColumn() - 1 > 0) {
                var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                    promotionMoves(myPosition, position, moves);
                }
            }
            return moves;
}

    public HashSet calculateBlackMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = ChessGame.TeamColor.BLACK;
        HashSet<ChessMove> moves = new HashSet<>();
        if (myPosition.getRow() == 7) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (board.getPiece(position) == null) {
                var newMove = new ChessMove(myPosition, position, null);
                moves.add(newMove);
                position = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn());
                if (board.getPiece(position) == null) {
                    newMove = new ChessMove(myPosition, position, null);
                    moves.add(newMove);
                }
            }
        }
        if (myPosition.getRow() - 1 > 1) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (board.getPiece(position) == null) {
                var newMove = new ChessMove(myPosition, position, null);
                moves.add(newMove);
            }
        }
        if (myPosition.getRow() - 1 == 1) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (board.getPiece(position) == null) {
                promotionMoves(myPosition, position, moves);
            }
        }
        if (myPosition.getRow() - 1 > 1 && myPosition.getColumn() + 1 < 9) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                var newMove = new ChessMove(myPosition, position, null);
                moves.add(newMove);
            }
        }
        if (myPosition.getRow() - 1 > 1 && myPosition.getColumn() - 1 > 0) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                var newMove = new ChessMove(myPosition, position, null);
                moves.add(newMove);
            }
        }
        if (myPosition.getRow() - 1 == 1 && myPosition.getColumn() - 1 > 0) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != pieceColor) {
                promotionMoves(myPosition, position, moves);
            }
            position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (board.getPiece(position) == null) {
                promotionMoves(myPosition, position, moves);
            }

        }
        if (myPosition.getRow() - 1 == 1 && myPosition.getColumn() + 1 < 9) {
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
            if ((board.getPiece(position) != null) && (board.getPiece(position).getTeamColor() != pieceColor)) {
               promotionMoves(myPosition, position, moves);
            }
            position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (board.getPiece(position) == null) {
                var newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.QUEEN);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.ROOK);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.BISHOP);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.KNIGHT);
                moves.add(newMove);
            }
        }
        return moves;
    }

    public void promotionMoves(ChessPosition myPosition, ChessPosition position, HashSet<ChessMove> moves){
        var newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.QUEEN);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.ROOK);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.BISHOP);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, position, ChessPiece.PieceType.KNIGHT);
        moves.add(newMove);
    }
    }
