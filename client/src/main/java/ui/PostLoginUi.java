package ui;

import java.util.Scanner;

public class PostLoginUi {

    private final PostLoginClient postLoginClient;

    public PostLoginUi(String authtoken, String url) {
        postLoginClient = new PostLoginClient(url, authtoken, this);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
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
