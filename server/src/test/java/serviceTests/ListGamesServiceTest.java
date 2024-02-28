package serviceTests;

import dataAccess.*;
import exception.BadRequest;
import exception.Unauthorized;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.JoinGameRequest;
import service.ClearService;
import service.ListGameService;

import java.util.ArrayList;

public class ListGamesServiceTest {
    private UserDAO userDao = new memoryUserDAO();
    private AuthDAO authDao = new memoryAuthDAO();
    private GameDAO gameDao = new memoryGameDAO();
    private String authToken;
    private int gameID;

    @BeforeEach
    public void init() throws Exception {
        UserData userData = new UserData("paul", "1234", "joe.com");
        ClearService clearService = new ClearService(userDao, authDao, gameDao);
        clearService.clear();
        userDao.addUser(userData);
        this.authToken = authDao.createAcount(userData).authToken();
        this.gameID = gameDao.createGame("yup");
        this.gameID = gameDao.createGame("partay");
        gameDao.joinGame("WHITE", gameID, new AuthData("paul", authToken));
    }

    @Test
    public void listGames() throws Exception {
        ListGameService listGameService = new ListGameService(userDao, authDao, gameDao);
        ArrayList<GameData> games = listGameService.getGames(authToken);
        Assertions.assertFalse(games.isEmpty());
    }

    @Test
    public void unauthorized() throws Exception {
        ListGameService listGameService = new ListGameService(userDao, authDao, gameDao);
        authDao.clear();
        Assertions.assertThrows(Unauthorized.class, () -> listGameService.getGames(authToken));
    }

}
