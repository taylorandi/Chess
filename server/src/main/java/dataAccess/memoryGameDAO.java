package dataAccess;
import model.GameData;
import response.GameResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class memoryGameDAO implements GameDAO{
    private Map<String, GameData> game = new HashMap<>();

    @Override
    public void clear(){
            game.clear();
    }

    @Override
    public ArrayList getGames(){
        ArrayList<GameResponse> games = new ArrayList<>();
        for (GameData value: game.values()) {
            GameResponse info = new GameResponse(value.gameId(), value.whiteUserName(), value.blackUserName(), value.gameName());
            games.add(info);
        }
        return games;
    }
}
