package serviceTests;

import dataAccess.*;
import exception.BadRequest;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import service.ClearService;
import service.CreateGameService;

public class CreateGameServiceTests {
    private UserDAO userDao = new memoryUserDAO();
    private AuthDAO authDao = new memoryAuthDAO();
    private GameDAO gameDao = new memoryGameDAO();
    private String authToken;

    @BeforeEach
    public void init() throws Exception {
        UserData userData = new UserData("paul", "1234", "joe.com");
        ClearService clearService = new ClearService(userDao, authDao, gameDao);
        clearService.clear();
        userDao.addUser(userData);
        this.authToken = authDao.createAccount(userData).authToken();
    }

    @Test
    public  void  CreateGame() throws Exception {
        CreateGameService createGameService = new CreateGameService(userDao, authDao, gameDao);
        int gameID = createGameService.createGame(authToken, new CreateGameRequest("Party time"));
        Assertions.assertTrue(gameID == 1);
    }

    @Test
    public void badRequest() throws Exception {
        CreateGameService createGameService = new CreateGameService(userDao, authDao, gameDao);

        Assertions.assertThrows(BadRequest.class, () -> createGameService.createGame(authToken, new CreateGameRequest(null)));
    }
}
