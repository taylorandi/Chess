package serviceTests;

import dataAccess.*;
import exception.BadRequest;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.JoinGameRequest;
import service.ClearService;

public class JoinGameServiceTests {
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
    }

    @Test
    public  void  joinGame() throws Exception {
        service.JoinGameService joinGameService = new service.JoinGameService(userDao, authDao, gameDao);
        joinGameService.joinGame(authToken, new JoinGameRequest("WHITE", gameID));
        GameData gameData = gameDao.getGame(gameID);
        Assertions.assertTrue(gameData.whiteUserName() != null);
    }

    @Test
    public void badRequest() throws Exception {
        service.JoinGameService joinGameService = new service.JoinGameService(userDao, authDao, gameDao);
        Assertions.assertThrows(BadRequest.class, () -> joinGameService.joinGame(authToken, new JoinGameRequest("WHITE", 0)));
    }
}
