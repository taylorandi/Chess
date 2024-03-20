package ui;

import server.ServerFacade;

import java.util.Scanner;

public class PostLoginUi {

    private final PostLoginClient postLoginClient;

    public PostLoginUi(String url, String authtoken) {
        postLoginClient = new PostLoginClient(url, authtoken, this);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        var result = "";
        System.out.print(EscapeSequences.SET_TEXT_ITALIC + EscapeSequences.SET_TEXT_COLOR_MAGENTA
        + "Welcome to 240 chess!!\n" + EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.RESET_TEXT_ITALIC);
        while (!result.equals("logout")) {
            String line = scanner.nextLine();

            try {
                result = postLoginClient.evaluate(line);
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var message = e.getMessage();
                System.out.print(message);
            }
        }
    }
}
