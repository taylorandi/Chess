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
import java.util.Arrays;

public class GamePlayClient {

    private final String authToken;
    private final ServerFacade server;
    public WebSocketFacade facade;

    private final Integer gameId;

    private static ChessBoard board;
    private static ChessGame.TeamColor color = null;

    public GamePlayClient(String url, String authToken, int gameId, ChessGame.TeamColor color, GameHandler gameHandler) throws DeploymentException, IOException, ResponseException, URISyntaxException {
        this.authToken = authToken;
        this.facade = new WebSocketFacade(url, gameHandler);
        this.gameId = gameId;
        this.server = new ServerFacade(url);
        this.board = new ChessBoard();
        this.color = color;
    }

    public static String evaluate(String line) {

        try{
            var tokens = line.toLowerCase().split(" ");
            var command = (tokens.length > 0) ? tokens[0] : "help";
            var parameters = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "help" -> help();
                case "reprint_board" -> printBoard();
                case "leave" -> "quit";

                default -> help();
            };
        } catch(Exception e){
            return e.getMessage();
        }
    }

    private static String printBoard() {
        if(color == ChessGame.TeamColor.WHITE){
           return printWhiteBoard();
        }
        else {
            return printBlackBoard();
        }
    }

    private static String help() {
        String out = EscapeSequences.SET_TEXT_COLOR_BLUE + "resign "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to resign lame\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "leave "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- to leave the game\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "reprint_board "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- reprint the board\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "see available moves <a-h> <1-8> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "to see all possible moves for piece\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "make_move (original position, new position) <a-h> <1-8> <a-h> <1-8> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- to make a move\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "help "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- understand possible commands\n"
                + EscapeSequences.SET_TEXT_COLOR_WHITE;
        return out;
    }


    private static String printBlackBoard() {
        Boolean alternate = true;
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


        return chessBoard;

    }

    private static String printWhiteBoard() {
        Boolean alternate = true;
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


        return chessBoard;

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
