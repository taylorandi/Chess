package chess.movesCalcuators;

import chess.*;

import java.util.HashSet;

public class PieceMovesCalculator {

    public HashSet pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition){
        ChessPiece piece = chessBoard.getPiece(chessPosition);
        switch (piece.getPieceType()){
            case PAWN:
                PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator();
                if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return pawnMovesCalculator.calculateWhiteMoves(chessBoard, chessPosition);
                }
                else {
                    return pawnMovesCalculator.calculateBlackMoves(chessBoard, chessPosition);
                }
            case ROOK:
                RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
                return rookMovesCalculator.calculateMoves(chessBoard, chessPosition);
            case KING:
                KingMovesCalculator kingMovesCalculator = new KingMovesCalculator();
                return kingMovesCalculator.calculateMoves(chessBoard, chessPosition);
            case KNIGHT:
                KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator();
                return knightMovesCalculator.calculateMoves(chessBoard, chessPosition);
            case BISHOP:
                BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
                return bishopMovesCalculator.calculateMoves(chessBoard, chessPosition);
            case QUEEN:
                QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator();
                return queenMovesCalculator.calculateMoves(chessBoard, chessPosition);

        }
        return null;
    }
}
