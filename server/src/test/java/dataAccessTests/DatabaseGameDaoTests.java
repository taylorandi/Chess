package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;

public class DatabaseGameDaoTests {
    private AuthDAO authDAO = null;
    private GameDAO gameDao = null;
    private UserDAO userDao = null;

    @BeforeEach
    public void init(){
        try{
            DatabaseManager.createDatabase();
            authDAO = new SqlAuthDAO();
            authDAO.clear();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
