package chess;

import chess.movesCalcuators.PieceMovesCalculator;

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


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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
        PieceMovesCalculator pieceMovesCalculator = new PieceMovesCalculator();
        return pieceMovesCalculator.pieceMoves(board, myPosition);
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
}
