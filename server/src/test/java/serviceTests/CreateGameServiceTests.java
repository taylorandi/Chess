package serviceTests;

import dataAccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import service.ClearService;

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
        this.authToken = authDao.createAcount(userData).authToken();
    }


}
