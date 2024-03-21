package ui;


import response.CreateGameResponse;
import response.GameResponse;
import response.ListGamesResponse;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;

public class PostLoginClient {

    private final String url;
    private final String authToken;
    private final ServerFacade server;
    private int game1;
    public PostLoginClient(String url, String authtoken ,PostLoginUi postLoginUi) {
        this.url = url;
        this.authToken = authtoken;
        this.server = new ServerFacade(url);
    }

    public String evaluate(String line){
        try{
            var tokens = line.toLowerCase().split(" ");
            var command = (tokens.length > 0) ? tokens[0] : "help";
            var parameters = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "help" -> help();
                case "create_game" -> createGame(parameters);
                case "logout" -> logOut();
                case "list_games" -> listGames();
                case "join_game" -> joinGame(parameters);
                case "join_observer" -> joinObserve(parameters);
                default -> help();
            };
        } catch(Exception e){
            return e.getMessage();
        }
    }

    private String joinObserve(String[] parameters) {
        try {
            server.joinGameObserverServerFacade(parameters, game1, authToken);
            GamePlayUi.run();
            return EscapeSequences.SET_TEXT_COLOR_MAGENTA
                    + "Welcome to the Lobby: enter a command or type help for a list of options"
                    + EscapeSequences.SET_TEXT_COLOR_WHITE;
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String joinGame(String[] parameters) {
        try {
            server.joinGameServerFacade(parameters, game1, authToken);
            GamePlayUi gamePlayUi = new GamePlayUi();
            GamePlayUi.run();
            return EscapeSequences.SET_TEXT_COLOR_MAGENTA
                    + "Welcome to the Lobby: enter a command or type help for a list of options"
                    + EscapeSequences.SET_TEXT_COLOR_WHITE;
        } catch (Exception e){
            return e.getMessage();
        }

    }

    private String listGames() {
        try {
            ListGamesResponse listGamesResponse = server.listGameServerFacade(authToken);
            ArrayList<GameResponse> games = listGamesResponse.getGames();
            String megaLongList = "";
            this.game1 = games.get(0).getGameID() - 1;
            for(int i = 0; i < games.size(); i++){
                megaLongList = megaLongList + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "game ID: " + EscapeSequences.SET_TEXT_COLOR_BLUE + (i + 1) + EscapeSequences.SET_TEXT_COLOR_MAGENTA +  " game name: " + EscapeSequences.SET_TEXT_COLOR_BLUE + games.get(i).getGameName()
                       + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " black player: " + EscapeSequences.SET_TEXT_COLOR_BLUE + games.get(i).getBlackUsername() + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " white player: " + EscapeSequences.SET_TEXT_COLOR_BLUE + games.get(i).getWhiteUsername() + "\n";
            }
            return megaLongList;
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String createGame(String[] parameters) {
        try {
           CreateGameResponse game = server.createGameServerFacade(parameters, authToken);
           int i = game.getGameID() - game1;
            return "Your game ID is: " + i;
        } catch (Exception e){
            return e.getMessage();
        }
    }

    private String help() {
        String out = EscapeSequences.SET_TEXT_COLOR_BLUE + "logout "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to logout\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "create_game <GAME NAME> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- to create a new game\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "list_games "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- list currently created games\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "join_game <PLAYER COLOR> <GAME ID> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- join the game with given game id\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "join_observer <GAME ID> "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- observe the game with given game id\n"
                + EscapeSequences.SET_TEXT_COLOR_BLUE + "help "
                + EscapeSequences.SET_TEXT_COLOR_MAGENTA + "- understand possible commands\n"
                + EscapeSequences.SET_TEXT_COLOR_WHITE;
        return out;
    }

    private String logOut() {
        try{
            server.logoutServerFacade(authToken);
            return "logout";
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
