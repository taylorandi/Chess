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



    public GamePlayUi(String url, String authtoken, int baseGame) throws DeploymentException, IOException, ResponseException, URISyntaxException {
        this.gamePlayClient = new GamePlayClient(url, authtoken, baseGame, this);


    }

    public static void run() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Game Successfully joined" + EscapeSequences.SET_TEXT_COLOR_WHITE);
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("logout")) {
            String line = scanner.nextLine();
            gamePlayClient.evaluate(String.valueOf(scanner));
        }
    }

    @Override
    public void updateGame(ChessGame game) {

    }

    @Override
    public void printMessage(JsonElement message) {

    }
}
