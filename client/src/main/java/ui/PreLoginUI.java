package ui;


import java.util.Scanner;

public class PreLoginUI {
    private final PreLoginClient preLoginClient;

    public PreLoginUI(String serverUrl) {
        preLoginClient = new PreLoginClient(serverUrl, this);
    }

    public void run(){
        System.out.println(EscapeSequences.WHITE_KING + EscapeSequences.SET_TEXT_ITALIC + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Welcome to 240 Chess: Type Help to get started. " + EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.RESET_TEXT_ITALIC + EscapeSequences.WHITE_KING);
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            String line = scanner.nextLine();

            try {
                result = preLoginClient.evaluate(line);
                System.out(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e){
                var message = e.getMessage();
                System.out.print(message);
            }


        }

    }
}
