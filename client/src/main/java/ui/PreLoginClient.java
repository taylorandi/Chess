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
            }
        }
    }
}
