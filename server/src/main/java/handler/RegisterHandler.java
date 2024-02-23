package handler;

import Register.RegisterRequest;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import Exception.*;

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
            register.registerUser(request);
        }catch (BadRequest e){
            response.status(400);
        }catch (AlreadyTaken e){
            response.status(403);
        }
        catch (Exception e){
            response.status(500);
        }
        return response;
        }
}
