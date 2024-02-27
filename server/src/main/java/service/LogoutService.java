package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import model.AuthData;
import spark.Request;

public class LogoutService {
    private AuthDAO authDao;
    private UserDAO userDao;
    private GameDAO gameDao;

    public LogoutService(AuthDAO authDao, UserDAO userDao, GameDAO gameDao) {
        this.authDao = authDao;
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    public void logout(Request request) throws Unauthorized {
        AuthData user;
        try{
            String authToken = request.headers("Authorization");
            if(authToken == null){
                throw new Unauthorized("ERROR: unauthorized");
            }
            authDao.logoutUser(authToken);
        } catch (Exception e) {
           throw new Unauthorized("ERROR: unauthorized");
        }
    }
}
