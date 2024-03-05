package dataAccess;

import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO {

    public void clear() throws DataAccessException;

    ArrayList getGames() throws DataAccessException;

    int createGame(String gameName) throws DataAccessException, BadRequest;

    void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken, DataAccessException;

    boolean verify(int gameId) throws DataAccessException;

    boolean isEmpty();

    GameData getGame(int gameID) throws DataAccessException, BadRequest;

}
