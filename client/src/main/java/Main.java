import chess.*;
import ui.PreLoginUI;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new PreLoginUI(serverUrl).run();
    }
}