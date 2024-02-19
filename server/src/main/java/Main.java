import chess.*;

import server.Server;
public class Main {

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        Server chessServer = new Server();
        chessServer.run(1234);
        System.out.println("♕ 240 Chess Server: " + piece);
    }
}