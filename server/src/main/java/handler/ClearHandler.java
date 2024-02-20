package handler;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final AuthDAO authDao;
    private final GameDAO gameDao;
    private final UserDAO userDao;

   public  ClearHandler(AuthDAO authDao, GameDAO gameDao, UserDAO userDao){

        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    public Object handleRequest(Request req, Response res){
       return null;
    }
}
