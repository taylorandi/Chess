package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
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

    public AuthData loginUser(Request request) throws Unauthorized {
        UserData user;
        try{
            LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            UserData loginUser = new UserData(username, password, null);
            boolean verified = userDao.login(loginUser);
            if (verified) {
                return authDao.createAccount(loginUser);
            }
            else{
                throw new Unauthorized("ERROR: unauthorized");
            }
        }catch (Exception e){
            throw new Unauthorized("ERROR: unauthorized");
        }
    }
}
