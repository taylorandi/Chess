package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.AlreadyTaken;
import exception.BadRequest;
import exception.Unauthorized;
import response.ExceptionMessage;
import service.CreateGameService;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public JoinGameHandler(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Object handleRequest(Request request, Response response){
        JoinGameService joinGameService = new JoinGameService(userDao, authDao, gameDao);
        try {
            joinGameService.joinGame(request);
        }catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        } catch (AlreadyTaken e){
            response.status(403);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        } catch (BadRequest e){
            response.status(400);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        } catch (Exception e){
            response.status(500);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));

        }
        return "{}";
    }
}
