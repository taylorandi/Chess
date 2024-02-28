package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.BadRequest;
import exception.Unauthorized;
import request.CreateGameRequest;
import response.CreateGameResponse;
import response.ExceptionMessage;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public CreateGameHandler(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Object handleRequest(Request request, Response response){
        try {
            CreateGameService createGameService = new CreateGameService(userDao, authDao, gameDao);
            String authToken = request.headers("Authorization");
            if (authToken == null || !authDao.verify(authToken)) {
                throw new Unauthorized("ERROR: unauthorized");
            }
            CreateGameRequest createGameRequest = new Gson().fromJson(request.body(), CreateGameRequest.class);
            return new Gson().toJson(new CreateGameResponse(createGameService.createGame(authToken, createGameRequest)));
        } catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }catch (BadRequest e){
            response.status(400);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }catch (Exception e){
            response.status(500);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }
    }
}
