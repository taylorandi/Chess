package serviceTests;

import dataAccess.*;
import exception.Unauthorized;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LogoutService;

public class LogoutServiceTests {
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

    @Test
    public void successfulLogout() throws Exception {
        LogoutService logoutService = new LogoutService(authDao, userDao, gameDao);
        logoutService.logout(authToken);
        Assertions.assertFalse(authDao.verify(authToken));

    }

    @Test
    public void alreadyLoggedOut() throws Exception {
        LogoutService logoutService = new LogoutService(authDao, userDao, gameDao);
        logoutService.logout(authToken);
        Assertions.assertThrows(Unauthorized.class, () -> logoutService.logout(authToken));
    }
}
