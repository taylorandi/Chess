package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
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

    public ArrayList getGames(String authToken) throws Unauthorized {
        try {
            if(!authDao.verify(authToken)){
                throw new Unauthorized("ERROR: unauthorized");
            }
            return gameDao.getGames();
        } catch (Exception e){
            throw new Unauthorized("ERROR: unauthorized");
        }
    }
}
