package serviceTests;

import dataAccess.*;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LoginService;
import serviceTests.request.RequestObjects;

public class LoginServiceTest {
    private UserDAO userDao = new memoryUserDAO();
    private AuthDAO authDao = new memoryAuthDAO();
    private GameDAO gameDao = new memoryGameDAO();
    @BeforeEach
    public void init() throws Exception {
        UserData userData = new UserData("paul", "1234", "joe.com");
        ClearService clearService = new ClearService(userDao, authDao, gameDao);
        clearService.clear();
        userDao.addUser(userData);
    }

    @Test
    public void loginUser() throws Exception {
        RequestObjects request = new RequestObjects("{ \"username\":\"paul\", \"password\":\"1234\" }");
        LoginService loginService = new LoginService(authDao, userDao, gameDao);
        AuthData authData = loginService.loginUser(request);
        Assertions.assertTrue(authData.authToken() != null);
    }

    @Test
    public void alreadyInUse() throws Exception{
        userDao.clear();
        RequestObjects request = new RequestObjects("{ \"username\":\"paul\", \"password\":\"1234\" }");
        LoginService loginService = new LoginService(authDao, userDao, gameDao);
        Assertions.assertThrows(Unauthorized.class, () -> loginService.loginUser(request));
    }

}
