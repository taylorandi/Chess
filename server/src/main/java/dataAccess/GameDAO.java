package dataAccess;

import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;

import java.util.ArrayList;

public interface GameDAO {

    public void clear();

    ArrayList getGames();

    int createGame(String gameName);

    void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken;

}
