package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import request.ListGamesRequest;
import request.LoginRequest;
import spark.Request;

import java.util.ArrayList;

public class ListGameService {

    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public ListGameService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public ArrayList getGames(Request request) throws Unauthorized {
        try {
            String authToken = request.headers("Authorization");
            if (authToken == null) {
                throw new Unauthorized("ERROR: unauthorized");
            }
            if(!authDao.verify(authToken)){
                throw new Unauthorized("ERROR: unauthorized");
            }
            return gameDao.getGames();
        } catch (Exception e){
            throw new Unauthorized("ERROR: unauthorized");
        }
    }
}
