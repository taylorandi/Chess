package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.BadRequest;
import exception.Unauthorized;
import request.CreateGameRequest;
import request.LoginRequest;
import server.Server;
import spark.Request;

public class CreateGameService {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public CreateGameService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public int createGame(Request request) throws Unauthorized, BadRequest {
        try {
            String authToken = request.headers("Authorization");
            if (authToken == null || !authDao.verify(authToken)) {
                throw new Unauthorized("ERROR: unauthorized");
            }
            CreateGameRequest createGameRequest = new Gson().fromJson(request.body(), CreateGameRequest.class);
            String gameName = createGameRequest.getGameName();
            if(gameName == null){
                throw new BadRequest("ERROR: bad request");
            }
            return gameDao.createGame(gameName);
        } catch (Unauthorized e){
            throw new Unauthorized("ERROR: unauthorized");
        } catch (Exception e){
            throw new BadRequest("ERROR: bad request");
        }
    }
}
