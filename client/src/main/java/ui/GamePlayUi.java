package ui;

import chess.ChessGame;
import com.google.gson.JsonElement;
import exception.ResponseException;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class GamePlayUi implements GameHandler {

    private static GamePlayClient gamePlayClient;
    private GameHandler gameHandler;



    public GamePlayUi(String url, String authtoken, int baseGame, ChessGame.TeamColor color) throws DeploymentException, IOException, ResponseException, URISyntaxException {
        this.gamePlayClient = new GamePlayClient(url, authtoken, baseGame, color, this);


    }

    public static void run() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Game Successfully joined" + EscapeSequences.SET_TEXT_COLOR_WHITE);
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();
            GamePlayClient.evaluate(String.valueOf(scanner));
        }
    }

    @Override
    public void updateGame(ChessGame game) {

    }

    @Override
    public void printMessage(JsonElement message) {

    }
}
