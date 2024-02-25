package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import service.RegisterService;
import spark.Request;
import spark.Response;


import exception.*;

public class RegisterHandler {
    private final AuthDAO authDao;
    private final GameDAO gameDao;
    private final UserDAO userDao;

    public RegisterHandler(AuthDAO authDao, GameDAO gameDao, UserDAO userDao) {

        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    public Object handleRequest(Request request, Response response) {
        RegisterService register = new RegisterService(authDao, gameDao, userDao);
        try {
            return new Gson().toJson(register.registerUser(request));
        }catch (BadRequest e){
            response.status(400);
            return new Gson().toJson(e);
        }catch (AlreadyTaken e){
            response.status(403);
            return new Gson().toJson(e);
        }
        catch (Exception e){
            response.status(500);
            return new Gson().toJson(e);
        }
        }
}
