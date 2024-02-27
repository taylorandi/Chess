package dataAccess;
import chess.ChessGame;
import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;
import response.GameResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class memoryGameDAO implements GameDAO{
    private Map<Integer, GameData> createdGames = new HashMap<>();
    private int gameId;

    @Override
    public void clear(){
        createdGames.clear();
        gameId = 0;
    }

    @Override
    public ArrayList getGames(){
        ArrayList<GameResponse> games = new ArrayList<>();
        for (GameData value: createdGames.values()) {
            GameResponse info = new GameResponse(value.gameId(), value.whiteUserName(), value.blackUserName(), value.gameName());
            games.add(info);
        }
        return games;
    }

    @Override
    public int createGame(String gameName){
        gameId += 1;
        GameData game = new GameData(gameId, null, null, gameName, new ChessGame());
        createdGames.put(gameId, game);
        return gameId;
    }

    @Override
    public void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken {
        if(createdGames.getOrDefault(gameId, null) == null){
            throw new BadRequest("ERROR: bad request");
        }
        GameData game = createdGames.get(gameId);
        if(playerColor.equals("BLACK")) {
            if (game.blackUserName() == null) {
                GameData newGame = new GameData(game.gameId(), game.whiteUserName(), player.username(), game.gameName(), game.game());
                createdGames.replace(gameId, newGame);
            } else {
                throw new AlreadyTaken("ERROR: already taken");
            }
        }
        else if(playerColor.equals("WHITE")){
                if(game.whiteUserName() == null){
                    GameData newGame = new GameData(game.gameId(), player.username(), game.blackUserName(), game.gameName(), game.game());
                    createdGames.replace(gameId, newGame);
                }
                else{
                    throw new AlreadyTaken("ERROR: already taken");
                }

        }
    }

}
