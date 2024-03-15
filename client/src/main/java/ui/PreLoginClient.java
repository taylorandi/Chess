package ui;

import server.ServerFacade;

import java.lang.reflect.Array;
import java.util.Arrays;


public class PreLoginClient {
    public PreLoginClient(String serverUrl, PreLoginUI preLoginUI) {
    }

    public String evaluate(String line) {
        try{
            var tokens = line.toLowerCase().split(" ");
            var command = (tokens.length > 0) ? tokens[0] : "help";
            var parameters = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "help" -> help();
                case "quit" -> "quit";
                case "login" -> login(parameters);
                case "register" -> register(parameters);
                default -> help();
            };
            } catch(Exception e){
                return e.getMessage();
        }
    }

    private String register(String[] parameters) {
        return "yo sup boi";
    }

    private String login(String[] parameters) {
        return "welcome \n";
    }

    private String help() {
        String out = EscapeSequences.SET_TEXT_COLOR_BLUE + "register <USERNAME> <PASSWORD> <EMAIL> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to create and account\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "login <USERNAME> <PASSWORD> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- to play chess\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "quit "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- exit the program\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "help "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- understand possible commands\n"
                + EscapeSequences.SET_TEXT_COLOR_WHITE;
        return out;
    }
}
