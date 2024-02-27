package chess.movesCalcuators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class BishopMovesCalculator {

    public HashSet calculateMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
        //up and left
        for(int i = 1; i < 8; i++) {
            if (myPosition.getRow() - i > 0 && myPosition.getColumn() - i > 0) {
                var position = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                if(checkMove(board, myPosition, position, moves)){
                    break;
                }
            }
        }
        // up and right
        for(int i = 1; i < 8; i++) {
            if (myPosition.getRow() - i > 0 && myPosition.getColumn() + i < 9) {
                var position = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                if(checkMove(board, myPosition, position, moves)){
                    break;
                }
            }
        }
        // down and left
        for(int i = 1; i < 8; i++) {
            if (myPosition.getRow() + i < 9 && myPosition.getColumn() - i > 0) {
                var position = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                if(checkMove(board, myPosition, position, moves)){
                    break;
                }
            }
        }
        // down and right
        for(int i = 1; i < 8; i++) {
            if (myPosition.getRow() + i < 9 && myPosition.getColumn() + i < 9) {
                var position = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                if(checkMove(board, myPosition, position, moves)){
                    break;
                }
            }
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
