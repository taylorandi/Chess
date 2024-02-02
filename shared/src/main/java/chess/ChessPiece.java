package chess;

import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    private boolean hasMoved;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        hasMoved = false;
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

    public void SetMoved(){
        this.hasMoved = true;
    }

    public boolean GetMoveStatus(){
        return !hasMoved;
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
               if(pieceColor == ChessGame.TeamColor.WHITE) {
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
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }
                   }
                   if (myPosition.getRow() + 1 < 8 && myPosition.getColumn() + 1 < 9) {
                       var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                       if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                           var newMove = new ChessMove(myPosition, position, null);
                           moves.add(newMove);
                       }
                   }
                   if (myPosition.getRow() + 1 < 8 && myPosition.getColumn() - 1 > 0) {
                       var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                       if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                           var newMove = new ChessMove(myPosition, position, null);
                           moves.add(newMove);
                       }
                   }
                   if (myPosition.getRow() + 1 == 8 && myPosition.getColumn() + 1 < 9) {
                       var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                       if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }
                   }
                       if (myPosition.getRow() + 1 == 8 && myPosition.getColumn() - 1 > 0) {
                           var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                           if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                               var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                               moves.add(newMove);
                               newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                               moves.add(newMove);
                               newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                               moves.add(newMove);
                               newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                               moves.add(newMove);
                           }
                   }
               }

                //black pieces
               else{
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
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }
                   }
                   if (myPosition.getRow() - 1 > 1 && myPosition.getColumn() + 1 < 9) {
                       var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                       if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                           var newMove = new ChessMove(myPosition, position, null);
                           moves.add(newMove);
                       }
                   }
                   if (myPosition.getRow() - 1 > 1 && myPosition.getColumn() - 1 > 0) {
                       var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                       if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                           var newMove = new ChessMove(myPosition, position, null);
                           moves.add(newMove);
                       }
                   }
                   if (myPosition.getRow() - 1 == 1 && myPosition.getColumn() - 1 > 0) {
                       var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                       if (board.getPiece(position) != null && board.getPiece(position).pieceColor != pieceColor) {
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }
                        position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
                       if (board.getPiece(position) == null) {
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }

                   }
                   if (myPosition.getRow() - 1 == 1 && myPosition.getColumn() + 1 < 9) {
                       var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                       if ((board.getPiece(position) != null) && (board.getPiece(position).pieceColor != pieceColor)) {
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }
                        position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
                       if (board.getPiece(position) == null) {
                           var newMove = new ChessMove(myPosition, position, PieceType.QUEEN);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.ROOK);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.BISHOP);
                           moves.add(newMove);
                           newMove = new ChessMove(myPosition, position, PieceType.KNIGHT);
                           moves.add(newMove);
                       }
                   }
               }
               break;
           case ROOK:
               for(int i = 1; i < 8; i++){
                   if(myPosition.getRow() + i < 9){
                       var position = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn());
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               for(int i = 1; i < 8; i++){
                   if(myPosition.getRow() - i > 0){
                       var position = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn());
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               for(int i = 1; i < 8; i++){
                   if(myPosition.getColumn() + i < 9){
                       var position = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i);
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               for(int i = 1; i < 8; i++){
                   if(myPosition.getColumn() - i > 0){
                       var position = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i);
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               break;
           case KNIGHT:
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
               break;
           case BISHOP:
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
               break;
           case QUEEN:
               for(int i = 1; i < 8; i++){
                   if(myPosition.getRow() + i < 9){
                       var position = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn());
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               for(int i = 1; i < 8; i++){
                   if(myPosition.getRow() - i > 0){
                       var position = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn());
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               for(int i = 1; i < 8; i++){
                   if(myPosition.getColumn() + i < 9){
                       var position = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i);
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
               for(int i = 1; i < 8; i++){
                   if(myPosition.getColumn() - i > 0){
                       var position = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i);
                       if(checkMove(board, myPosition, position, moves)){
                           break;
                       }
                   }
               }
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
               break;
           case KING:
               if(myPosition.getRow() + 1 < 9){
                   var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getRow() - 1 > 0){
                   var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getColumn() + 1 < 9){
                   var position = new ChessPosition(myPosition.getRow() , myPosition.getColumn() + 1);
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getColumn() - 1 > 0){
                   var position = new ChessPosition(myPosition.getRow() , myPosition.getColumn() - 1);
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getRow() + 1 < 9 && myPosition.getColumn() + 1 < 9){
                   var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getRow() - 1 > 0 && myPosition.getColumn() - 1 > 0){
                   var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getRow() - 1 > 0 && myPosition.getColumn() + 1 < 9){
                   var position = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                   checkMove(board, myPosition, position, moves);
               }
               if(myPosition.getRow() + 1 < 9 && myPosition.getColumn() - 1 > 0){
                   var position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                   checkMove(board, myPosition, position, moves);
               }
               if(!hasMoved){
                   for(int i = 1; i < 9; i++){
                       if(myPosition.getColumn() + i < 9) {
                           var position = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i);
                           if(board.getPiece(position) != null) {
                               if (board.getPiece(position).getPieceType() != PieceType.ROOK) {
                                   break;
                               } else if (board.getPiece(position).getPieceType() == PieceType.ROOK && board.getPiece(position).getTeamColor() == pieceColor && board.getPiece(position).GetMoveStatus() && position.getColumn() == 8) {
                                   var move = new ChessMove(myPosition, new ChessPosition(position.getRow(), position.getColumn() - 1), null);
                                   moves.add(move);
                               }
                           }
                       }
                   }
               }
               if(!hasMoved){
                   for(int i = 1; i < 9; i++){
                       if(myPosition.getColumn() - i > 0) {
                           var position = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i);
                           if(board.getPiece(position) != null) {
                               if (board.getPiece(position).getPieceType() != PieceType.ROOK) {
                                   break;
                               } else if (board.getPiece(position).getPieceType() == PieceType.ROOK && board.getPiece(position).getTeamColor() == pieceColor && board.getPiece(position).GetMoveStatus() && position.getColumn() == 1) {
                                   var move = new ChessMove(myPosition, new ChessPosition(position.getRow(), myPosition.getColumn() - 2), null);
                                   moves.add(move);
                               }
                           }
                       }
                   }
               }
               break;
       }
       return moves;
    }

    private boolean checkMove(ChessBoard board, ChessPosition myPosition, ChessPosition position, HashSet<ChessMove> moves ) {
        if (board.getPiece(position) == null) {
            var move = new ChessMove(myPosition, position, null);
            moves.add(move);
        } else {
            if (board.getPiece(position).getTeamColor() != pieceColor) {
                var move = new ChessMove(myPosition, position, null);
                moves.add(move);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if(this.getTeamColor() == ChessGame.TeamColor.WHITE){
            if(this.getPieceType() == PieceType.BISHOP){
                return "B";
            }
            else if(this.getPieceType() == PieceType.KNIGHT){
                return "N";
            }
            else if(this.getPieceType() == PieceType.ROOK){
                return "R";
            }
            else if(this.getPieceType() == PieceType.QUEEN){
                return "Q";
            }
            else if(this.getPieceType() == PieceType.KING){
                return "K";
            }
            else {
                return "P";
            }
        }
        else  {
            if (this.getPieceType() == PieceType.BISHOP) {
                return "b";
            }
            else if (this.getPieceType() == PieceType.KNIGHT) {
                return "n";
            }
            else if (this.getPieceType() == PieceType.ROOK) {
                return "r";
            }
            else if (this.getPieceType() == PieceType.QUEEN) {
                return "q";
            }
            else if (this.getPieceType() == PieceType.KING) {
                return "k";
            }
            else {
                return "p";
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
