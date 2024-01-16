package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {

        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {

        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {

        return promotionPiece;
    }

    @Override

    public String toString() {
        var start_row = getStartPosition().getRow();
        var start_col = getStartPosition().getColumn();
        var end_row = getEndPosition().getRow();
        var end_col = getEndPosition().getColumn();

        var startString = "{" + String.valueOf(start_row) + ", " + String.valueOf(start_col) + "}";
        var endString = "{" + String.valueOf(end_row) + ", " + String.valueOf(end_col) + "}";
        var tran = "->";

        return endString;
    }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (obj == this) {
                return true;
            } else if (obj.getClass() != this.getClass()) {
                return false;
            }
            ChessMove p = (ChessMove)obj;
            return (this.getEndPosition().equals(p.getEndPosition()) && this.getStartPosition().equals(p.getStartPosition()) && this.getPromotionPiece().equals(p.getPromotionPiece()));

        }
    }
