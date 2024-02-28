package serviceTests;

import dataAccess.*;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

public class ClearServiceTest {
    private AuthDAO authDAO = new memoryAuthDAO();
    private GameDAO gameDao = new memoryGameDAO();
    private UserDAO userDao = new memoryUserDAO();

    @BeforeEach
    public void init(){
        ClearService clearService = new ClearService(userDao, authDAO, gameDao);
        clearService.clear();
    }

    @Test
    public void clearService() throws Exception {

        try {
            userDao.addUser(new UserData("paul", "1234", "pauljones@jo.com"));
        }catch (Exception e){
            throw new Exception("ERROR: couldn't add user");
        }
        try {
            authDAO.createAcount(new UserData("paul", "1234", "pauljones@jo.com"));
        } catch (Exception e){
            throw new Exception("ERROR: couldn't create auth");
        }
        try {
            gameDao.createGame("pops");
        } catch (Exception e){
            throw new Exception("ERROR: couldn't create game");
        }
        ClearService clearService = new ClearService(userDao, authDAO, gameDao);
        clearService.clear();
        Assertions.assertTrue(authDAO.isEmpty() && userDao.isEmpty() && gameDao.isEmpty());

    }


}
