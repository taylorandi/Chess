package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
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
            return new Gson().toJson(login.loginUser(request));
        }catch (Unauthorized e){
            response.status(401);
            return new Gson().toJson(e.getMessage());
        }
        catch (Exception e){
            response.status(500);
            return new Gson().toJson(e);
        }

    }
}
