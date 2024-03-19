package ui;

import model.UserData;
import response.LoginResponse;
import server.ServerFacade;

import java.lang.reflect.Array;
import java.util.Arrays;


public class PreLoginClient {

    private final String serverUrl;
    private final ServerFacade server;
    public PreLoginClient(String serverUrl, PreLoginUI preLoginUI) {
        this.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl);
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
        if(parameters.length < 3){
            return "ERROR: invalid input";
        }
        String username = parameters[0];
        String password = parameters[1];
        String email = parameters[2];
        UserData user = new UserData(username, password, email);
        try {
            LoginResponse login = server.makeRequest("POST", "/user", user, LoginResponse.class);
            PostLoginUi postLoginUi = new PostLoginUi(serverUrl, login.getAuthToken());
            postLoginUi.run();
            return "Welcome: " + login.getUsername();
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String login(String[] parameters) {
        if(parameters.length < 2){
            return "ERROR: invalid input";
        }
        String username = parameters[0];
        String password = parameters[1];
        UserData user = new UserData(username, password, null);
        try {
            LoginResponse login = server.makeRequest("POST", "/session", user, LoginResponse.class);
            PostLoginUi postLoginUi = new PostLoginUi(serverUrl, login.getAuthToken());
            postLoginUi.run();
            return "Welcome: " + login.getUsername();
        } catch (Exception e){
            return e.getMessage();
        }
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
