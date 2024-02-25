package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import model.UserData;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import spark.Request;

public class LoginService {
    private final AuthDAO authDao;
    private final UserDAO userDao;
    private final GameDAO gameDao;

    public LoginService(AuthDAO authDao, UserDAO userDao, GameDAO gameDao) {

        this.authDao = authDao;
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    public Object loginUser(Request request) throws Unauthorized {
        UserData user;
        try{
            LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            UserData loginUser = new UserData(username, password, null);
            boolean verified = userDao.login(loginUser);
            if (verified) {
                String token = authDao.createAcount(loginUser);
                return new LoginResponse(loginUser.username(), token);
            }
            else{
                throw new Unauthorized("unauthorized");
            }
        }catch (Exception e){
            throw new Unauthorized("unauthorized");
        }
    }
}
