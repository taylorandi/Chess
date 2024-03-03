package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import dataAccess.memoryUserDAO;

public class ClearService {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public ClearService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }
    public void clear() {
        try {
            userDao.clear();
            authDao.clear();
            gameDao.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
