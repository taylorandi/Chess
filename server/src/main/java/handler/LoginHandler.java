package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import model.AuthData;
import response.ExceptionMessage;
import response.LoginResponse;
import response.RegisterResponse;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private final AuthDAO authDao;
    private final GameDAO gameDao;
    private final UserDAO userDao;

    public LoginHandler(AuthDAO authDao, GameDAO gameDao, UserDAO userDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    public Object handleRequest(Request request, Response response){
        LoginService login = new LoginService(authDao, userDao, gameDao);
        try{
            AuthData user = login.loginUser(request);
            return new Gson().toJson(new LoginResponse(user.username(), user.authToken()));
        }catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }
        catch (Exception e){
            response.status(500);
            return new Gson().toJson(new ExceptionMessage(e.getMessage()));
        }

    }
}
