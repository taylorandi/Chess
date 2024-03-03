package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;

public class DatabaseUserDaoTests {
    public class DatabaseAuthDaoTests {
        private AuthDAO authDAO = null;
        private GameDAO gameDao = new memoryGameDAO();
        private UserDAO userDao = new memoryUserDAO();

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
