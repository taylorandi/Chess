package dataAccess;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class memoryGameDAO implements GameDAO{
    private Map<String, GameData> game = new HashMap<>();

    @Override
    public void clear(){
            game.clear();
    }
}
