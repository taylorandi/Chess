package ui;

import java.util.Arrays;

public class PostLoginClient {

    private final String url;
    private final String authToken;
    public PostLoginClient(String url, String authtoken, PostLoginUi postLoginUi) {
        this.url = url;
        this.authToken = authtoken;
    }

    public String evaluate(String line){
        try{
            var tokens = line.toLowerCase().split(" ");
            var command = (tokens.length > 0) ? tokens[0] : "help";
            var parameters = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "help" -> "ya";
                case "quit" -> "quit";
                case "login" -> "";
                case "register" -> " ";
                default -> "  ";
            };
        } catch(Exception e){
            return e.getMessage();
        }
    }
}
