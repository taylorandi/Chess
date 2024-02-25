package service;

import request.RegisterRequest;
import response.RegisterResponse;
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

    public Object registerUser(Request request) throws BadRequest, AlreadyTaken {
        UserData user;
        try {
            RegisterRequest regesteredRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
            String userName = RegisterRequest.getUserName();
            String password = RegisterRequest.getPassword();
            String email = RegisterRequest.getEmail();
            user = new UserData(userName, password, email);
        } catch (Exception e){
            throw new BadRequest("bad request");
        }
        try {
            userDao.addUser(user);
            String token = authDao.createAcount(user);
            return new RegisterResponse(user.username(), token);
        } catch (Exception e){
            throw new AlreadyTaken("Username is already taken");
        }
    }
}
