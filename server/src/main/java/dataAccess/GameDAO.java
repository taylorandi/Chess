package dataAccess;

import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO {

    public void clear() throws DataAccessException;

    ArrayList getGames();

    int createGame(String gameName) throws DataAccessException;

    void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken;

    boolean verify(int gameId);

    boolean isEmpty();

    GameData getGame(int gameID);

}
