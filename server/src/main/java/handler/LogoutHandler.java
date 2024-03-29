package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import response.ExceptionMessage;
import service.LoginService;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public LogoutHandler(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {

        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Object handleRequest(Request request, Response response){
        LogoutService logout = new LogoutService(authDao, userDao, gameDao);
            try{
                String authToken = request.headers("Authorization");
                if(authToken == null){
                    throw new Unauthorized("ERROR: unauthorized");
                }
            logout.logout(authToken);
            return "{}";
        } catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        } catch (Exception e){
            response.status(500);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }
    }
}
