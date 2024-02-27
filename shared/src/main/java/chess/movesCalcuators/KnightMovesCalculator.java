package chess.movesCalcuators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class KnightMovesCalculator {


    public HashSet calculateMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
        if(myPosition.getRow() + 1 < 9 && myPosition.getColumn() + 2 < 9){
            var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() + 2 < 9 && myPosition.getColumn() + 1 < 9){
            var position = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() + 1 < 9 && myPosition.getColumn() - 2 > 0){
            var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 2);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() + 2 < 9 && myPosition.getColumn() - 1 > 0){
            var position = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() - 1 > 0 && myPosition.getColumn() + 2 < 9){
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 2);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() - 2 > 0 && myPosition.getColumn() + 1 < 9){
            var position = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() + 1);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() - 1 > 0 && myPosition.getColumn() - 2 > 0){
            var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 2);
            checkMove(board, myPosition, position, moves);
        }
        if(myPosition.getRow() - 2 > 0 && myPosition.getColumn() - 1 > 0){
            var position = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() -1);
            checkMove(board, myPosition, position, moves);
        }
        return moves;
    }
    private boolean checkMove(ChessBoard board, ChessPosition myPosition, ChessPosition position, HashSet<ChessMove> moves ) {
        if (board.getPiece(position) == null) {
            var move = new ChessMove(myPosition, position, null);
            moves.add(move);
        } else {
            if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                var move = new ChessMove(myPosition, position, null);
                moves.add(move);
            }
            return true;
        }
        return false;
    }
}
