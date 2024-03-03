package dataAccessTests;

import dataAccess.*;
import exception.AlreadyTaken;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class DatabaseUserDaoTests {
        private AuthDAO authDAO = null;
        private GameDAO gameDao = new memoryGameDAO();
        private UserDAO userDao = null;


        @BeforeEach
        public void init() {
            try {
                DatabaseManager.createDatabase();
                authDAO = new SqlAuthDAO();
                userDao = new SqlUserDAO();
                authDAO.clear();
                userDao.clear();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Test
        public void clear() {
            UserData newUser = new UserData("Ligeia Nüwa", "Cassandra Bláthíne", "job.com");
            try {
                userDao.addUser(newUser);
                userDao.clear();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
            Assertions.assertTrue(userDao.isEmpty());
        }

        @Test
        public void createNewUser() {
            UserData newUser = new UserData("Dalia Clotho", "Cassandra Bláthíne", "job.com");
            Assertions.assertDoesNotThrow(() -> userDao.addUser(newUser));
        }

        @Test
    public void alreadyTaken(){
            UserData newUser = new UserData("Melia Zephyr", "Cassandra Bláthíne", "job.com");
            try {
                userDao.addUser(newUser);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
            Assertions.assertThrows(AlreadyTaken.class, () -> userDao.addUser(newUser));
        }

    @Test
    public void login() throws DataAccessException {
        UserData newUser = new UserData("Leander Onnophris", "Cassandra Bláthíne", "job.com");
        try {
            userDao.addUser(newUser);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(userDao.login(newUser));
    }

    @Test
    public void invalidLogin() throws DataAccessException {
        UserData newUser = new UserData("Mithra Mut", "Larisa Lycurgus", "job.com");
        UserData badPassword = new UserData("Mithra Mut", "Baladeva Nuadu", "job.com");
        try {
            userDao.addUser(newUser);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(userDao.login(badPassword));
    }

    }


