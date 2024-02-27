package handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import response.ExceptionMessage;
import response.GameResponse;
import service.ListGameService;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

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
            ArrayList games = listGame.getGames(request);
            JsonArray gamesJson = new JsonArray();
            for(Object game : games) {
                gamesJson.add(new Gson().toJson(game));
            }
            return new Gson().toJson(listGame.getGames(request));
        } catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        } catch (Exception e){
            response.status(500);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }

    }
}
