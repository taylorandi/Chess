package ui;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Scanner;

public class GamePlayUi {

    private static GamePlayClient gamePlayClient;



    public GamePlayUi(String url, String authtoken, int baseGame) throws DeploymentException, IOException {
        this.gamePlayClient = new GamePlayClient(url, authtoken, baseGame);

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

}
