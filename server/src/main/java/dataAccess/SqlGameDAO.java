package dataAccess;

import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class SqlGameDAO implements GameDAO{
    @Override
    public void clear() {

    }

    @Override
    public ArrayList getGames() {
        return null;
    }

    @Override
    public int createGame(String gameName) {
        return 0;
    }

    @Override
    public void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken {

    }

    @Override
    public boolean verify(int gameId) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }
}
