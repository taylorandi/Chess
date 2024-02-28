package serviceTests;

import dataAccess.*;
import exception.AlreadyTaken;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.RegisterService;
import serviceTests.request.RequestObjects;

public class RegisterServiceTest {
    private UserDAO userDao = new memoryUserDAO();
    private AuthDAO authDao = new memoryAuthDAO();
    private GameDAO gameDao = new memoryGameDAO();
    @BeforeEach
    public void init() throws Exception {
        ClearService clearService = new ClearService(userDao, authDao, gameDao);
        clearService.clear();
    }

    @Test
    public void successRegister() throws Exception{
        RequestObjects request = new RequestObjects("{ \"username\":\"paul\", \"password\":\"1234\", \"email\":\"joe.com\" }");
        RegisterService registerService = new RegisterService(authDao, gameDao, userDao);
        AuthData authData = registerService.registerUser(request);
        Assertions.assertTrue(authData.authToken() != null);
    }
    @Test
    public void alreadyInUse() throws Exception{
        userDao.addUser(new UserData("paul", "1234", "joe.com"));
        RequestObjects request = new RequestObjects("{ \"username\":\"paul\", \"password\":\"1234\", \"email\":\"joe.com\" }");
        RegisterService registerService = new RegisterService(authDao, gameDao, userDao);
        Assertions.assertThrows(AlreadyTaken.class, () -> registerService.registerUser(request));
    }

}
