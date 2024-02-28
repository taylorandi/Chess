package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import response.ExceptionMessage;
import response.ListGamesResponse;
import service.ListGameService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public ListGameHandler(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Object handleRequest(Request request, Response response){
        ListGameService listGame =  new ListGameService(userDao, authDao, gameDao);
        try {
            String authToken = request.headers("Authorization");
            if (authToken == null) {
                throw new Unauthorized("ERROR: unauthorized");
            }
            return new Gson().toJson(new ListGamesResponse(listGame.getGames(authToken)));
        } catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        } catch (Exception e){
            response.status(500);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }

    }
}
