package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamColor;
    private ChessBoard board = new ChessBoard();


    public ChessGame() {
        setTeamTurn(TeamColor.WHITE);
        board.resetBoard();
        setBoard(this.board);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        var piece = board.getPiece(startPosition);
        var color = piece.getTeamColor();
        HashSet<ChessMove> validMoves = new HashSet<>();
        var moves = piece.pieceMoves(board, startPosition);
        for (var move : moves) {
            var replacedPiece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getStartPosition(), null);
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), piece);
            }
            if (move.getPromotionPiece() != null) {
                piece = new ChessPiece(color, move.getPromotionPiece());
                board.addPiece(move.getEndPosition(), piece);
            }

            boolean checkCheck = isInCheck(color);

            board.addPiece(move.getEndPosition(), replacedPiece);
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getStartPosition(), piece);
            }

            if (null != move.getPromotionPiece()) {
                piece = new ChessPiece(color, ChessPiece.PieceType.PAWN);
                board.addPiece(move.getStartPosition(), piece);
            }
            if (!checkCheck) {
                validMoves.add(move);
            }
        }
        if(piece.getPieceType() == ChessPiece.PieceType.KING){
            var pathMove = new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1), null);
            if(!validMoves.contains(pathMove)){
                var move = new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 2), null);
                validMoves.remove(move);
            }
            pathMove = new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 1), null);
            if(!moves.contains(pathMove)){
                var move = new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 2), null);
                validMoves.remove(move);
            }
        }
        System.out.println();
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        var piece = board.getPiece(move.getStartPosition());
        if (piece.getTeamColor() != this.getTeamTurn()) {
            throw new InvalidMoveException("color: " + piece.getTeamColor() + " doesn't match: " + teamColor);
        }
        var validMoves = validMoves(move.getStartPosition());
        if (validMoves.contains(move)) {
            board.addPiece(move.getStartPosition(), null);
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), piece);
            }


            if (move.getPromotionPiece() != null) {
                piece = new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
                board.addPiece(move.getEndPosition(), piece);
            }

            if (getTeamTurn() == TeamColor.BLACK) {
                setTeamTurn(TeamColor.WHITE);
            } else {
                setTeamTurn(TeamColor.BLACK);
            }
        } else {
            throw new InvalidMoveException();
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king = null;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                var position = new ChessPosition(i, j);
                var piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                        king = new ChessPosition(position.getRow(), position.getColumn());
                        break;
                    }
                }
            }
        }
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                var position = new ChessPosition(i, j);
                var piece = board.getPiece(position);
                if ((piece != null) && (piece.getTeamColor() != teamColor)) {
                    var validMov = piece.pieceMoves(board, position);
                    var move = new ChessMove(position, king, null);
                    if (validMov.contains(move) || validMov.contains(new ChessMove(position, king, ChessPiece.PieceType.QUEEN))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        return isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                var position = new ChessPosition(i, j);
                var piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    var moves = validMoves(position);
                    if (!moves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamColor == chessGame.teamColor && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamColor=" + teamColor +
                ", board=" + board +
                '}';
    }
}
