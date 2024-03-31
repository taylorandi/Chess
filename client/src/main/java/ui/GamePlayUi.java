package ui;

import java.util.Scanner;

public class GamePlayUi {

    public GamePlayUi(String url, String authtoken) {
        GamePlayClient gamePlayClient = new GamePlayClient(url, authtoken);
    }

    public static void run() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Game Successfully joined" + EscapeSequences.SET_TEXT_COLOR_WHITE);
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("logout")) {
            String line = scanner.nextLine();

            try {
                result = GamePlayClient.evaluate(line);
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var message = e.getMessage();
                System.out.print(message);
            }
        }
    }

}
