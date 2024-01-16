package chess;

import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

       return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {

       return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
       HashSet<ChessMove> moves =  new HashSet<>();
       switch (type) {
           case PAWN:
               break;
           case ROOK:
               break;
           case KNIGHT:
               break;
           case BISHOP:
               //up and left
               for(int i = 1; i < 8; i++) {
                   if (myPosition.getRow() - i > 0 && myPosition.getColumn() - i > 0) {
                       var position = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                       if (board.getPiece(position) == null) {
                           var move = new ChessMove(myPosition, position, null);
                           moves.add(move);
                       } else {
                            if(board.getPiece(position).getTeamColor() != pieceColor){
                                var move = new ChessMove(myPosition, position, null);
                                moves.add(move);
                            }
                            else{
                                break;
                            }

                       }
                   }
                   else{
                       break;
                   }

           }
               // up and right
               for(int i = 1; i < 8; i++) {
                   if (myPosition.getRow() - i > 0 && myPosition.getColumn() + i < 9) {
                       var position = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                       if (board.getPiece(position) == null) {
                           var move = new ChessMove(myPosition, position, null);
                           moves.add(move);
                       } else {
                           if (board.getPiece(position).getTeamColor() != pieceColor) {
                               var move = new ChessMove(myPosition, position, null);
                               moves.add(move);
                           } else {
                               break;
                           }

                       }
                   } else {
                       break;
                   }
               }
               // down and left
               for(int i = 1; i < 8; i++) {
                   if (myPosition.getRow() + i < 9 && myPosition.getColumn() - i > 0) {
                       var position = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                       if (board.getPiece(position) == null) {
                           var move = new ChessMove(myPosition, position, null);
                           moves.add(move);
                       } else {
                           if (board.getPiece(position).getTeamColor() != pieceColor) {
                               var move = new ChessMove(myPosition, position, null);
                               moves.add(move);
                           } else {
                               break;
                           }

                       }
                   } else {
                       break;
                   }
               }
                   // down and right
                   for(int i = 1; i < 8; i++) {
                       if (myPosition.getRow() + i < 9 && myPosition.getColumn() + i < 9) {
                           var position = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                           if (board.getPiece(position) == null) {
                               var move = new ChessMove(myPosition, position, null);
                               moves.add(move);
                           } else {
                               if (board.getPiece(position).getTeamColor() != pieceColor) {
                                   var move = new ChessMove(myPosition, position, null);
                                   moves.add(move);
                               } else {
                                   break;
                               }

                           }
                       } else {
                           break;
                       }
                   }
               break;
           case QUEEN:
               break;
           case KING:
               break;
       }
       return moves;
    }
}
