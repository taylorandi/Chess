package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import model.AuthData;
import request.LogoutRequest;
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
            LogoutRequest logoutRequest = new Gson().fromJson(request.body(), LogoutRequest.class);
            AuthData logout = new AuthData(null, logoutRequest.getAuthToken());
            authDao.logoutUser(logout);
        } catch (Exception e) {
           throw new Unauthorized("unauthorized");
        }
    }
}
