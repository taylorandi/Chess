package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ResponseException;
import server.ServerFacade;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

public class GamePlayClient {

    private final String authToken;
    private final ServerFacade server;
    public WebSocketFacade facade;

    private final Integer gameId;

    public GamePlayClient(String url, String authToken, int gameId, GameHandler gameHandler) throws DeploymentException, IOException, ResponseException, URISyntaxException {
        this.authToken = authToken;
        this.facade = new WebSocketFacade(url, gameHandler);
        this.gameId = gameId;
        this.server = new ServerFacade(url);

    }

    public static String evaluate(String line) {
        return "";
    }


    private static void printBlackBoard(ChessBoard board) {
        Boolean alternate = true;
        board.resetBoard();
        String chessBoard = "";
        chessBoard = EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.EMPTY
                + " a "  + " b " + " c " + " d " +  " e " + " f "
                + " g " + " h " + EscapeSequences.EMPTY + EscapeSequences.SET_BG_COLOR_BLACK + "\n";
        for (int i = 1; i < 9; i++){
            chessBoard += EscapeSequences.SET_BG_COLOR_LIGHT_GREY + " " + i + " ";
            for(int j = 1; j < 9; j++){
                ChessPiece piece = null;
                if(alternate == true){
                    chessBoard += EscapeSequences.SET_BG_COLOR_WHITE + getPiece(new ChessPosition(i, j), board);
                    alternate = false;
                }
                else{

                    chessBoard += EscapeSequences.SET_BG_COLOR_MAGENTA + getPiece(new ChessPosition(i, j), board);
                    alternate = true;
                }
            }
            chessBoard += EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " " + i + " " + EscapeSequences.SET_BG_COLOR_BLACK + "\n";
            if(alternate) {
                alternate = false;
            }
            else {
                alternate = true;
            }
        }
        chessBoard += EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.EMPTY
                + " a "  + " b " + " c " + " d " +  " e " + " f "
                + " g " + " h " + EscapeSequences.EMPTY + EscapeSequences.SET_BG_COLOR_BLACK + "\n \n \n";


        System.out.print(chessBoard);

    }

    private static void printWhiteBoard(ChessBoard board) {
        Boolean alternate = true;
        board.resetBoard();
        String chessBoard = "";
        chessBoard = EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.EMPTY
                + " h "  + " g " + " f " + " e " +  " d " + " c "
                + " b " + " a " + EscapeSequences.EMPTY + EscapeSequences.SET_BG_COLOR_BLACK + "\n";
        for (int i = 8; i > 0; i--){
            chessBoard += EscapeSequences.SET_BG_COLOR_LIGHT_GREY + " " + i + " ";
            for(int j = 8; j > 0; j--){
                ChessPiece piece = null;
                if(alternate == true){
                    chessBoard += EscapeSequences.SET_BG_COLOR_WHITE + getPiece(new ChessPosition(i, j), board);
                    alternate = false;
                }
                else{

                    chessBoard += EscapeSequences.SET_BG_COLOR_MAGENTA + getPiece(new ChessPosition(i, j), board);
                    alternate = true;
                }
            }
            chessBoard += EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " " + i + " " + EscapeSequences.SET_BG_COLOR_BLACK + "\n";
            if(alternate) {
                alternate = false;
            }
            else {
                alternate = true;
            }
        }
        chessBoard += EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.EMPTY
                + " h "  + " g " + " f " + " e " +  " d " + " c "
                + " b " + " a " + EscapeSequences.EMPTY + EscapeSequences.SET_BG_COLOR_BLACK + "\n \n \n";


        System.out.print(chessBoard);

    }

    private static String getPiece(ChessPosition position, ChessBoard board) {
        ChessPiece piece = board.getPiece(position);
        if (piece == null) {
            return EscapeSequences.EMPTY;
        }
        switch (piece.getPieceType()) {
            case KNIGHT:
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.WHITE_KNIGHT;
                } else {
                    return EscapeSequences.SET_TEXT_COLOR_RED + EscapeSequences.BLACK_KNIGHT;
                }
            case KING:
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.WHITE_KING;
                } else {
                    return EscapeSequences.SET_TEXT_COLOR_RED + EscapeSequences.BLACK_KING;
                }
            case ROOK:
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.WHITE_ROOK;
                } else {
                    return EscapeSequences.SET_TEXT_COLOR_RED + EscapeSequences.BLACK_ROOK;
                }
            case BISHOP:
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.WHITE_BISHOP;
                } else {
                    return EscapeSequences.SET_TEXT_COLOR_RED + EscapeSequences.BLACK_BISHOP;
                }
            case PAWN:
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.WHITE_PAWN;
                } else {
                    return EscapeSequences.SET_TEXT_COLOR_RED + EscapeSequences.BLACK_PAWN;
                }
            case QUEEN:
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.WHITE_QUEEN;
                } else {
                    return EscapeSequences.SET_TEXT_COLOR_RED + EscapeSequences.BLACK_QUEEN;
                }
            default:
                return EscapeSequences.EMPTY;
        }
    }

}
