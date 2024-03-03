package service;

import model.AuthData;
import request.RegisterRequest;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.UserData;
import spark.Request;
import exception.BadRequest;
import exception.AlreadyTaken;

public class RegisterService {


    private final AuthDAO authDao;
    private final GameDAO gameDao;
    private final UserDAO userDao;

    public RegisterService(AuthDAO authDao, GameDAO gameDao, UserDAO userDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    public AuthData registerUser(Request request) throws BadRequest, AlreadyTaken {
        UserData user;
        try {
            RegisterRequest registeredRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
            String userName = registeredRequest.getUsername();
            String password = registeredRequest.getPassword();
            String email = registeredRequest.getEmail();
            if(userName == null || password == null || email == null){
                throw new BadRequest("ERROR: bad request");
            }
            user = new UserData(userName, password, email);
        } catch (Exception e){
            throw new BadRequest("ERROR: bad request");
        }
        try {
            userDao.addUser(user);
            return authDao.createAccount(user);
        } catch (Exception e){
            throw new AlreadyTaken("ERROR: already taken");
        }
    }
}
