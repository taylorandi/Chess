package handler;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import service.ClearService;
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
       ClearService clear = new ClearService(userDao, authDao, gameDao);
       try {
           clear.clear();
           Object empty = "{}";
           return empty;
       } catch (Exception e){
           res.status(500);
           return e.getMessage();
       }


    }
}
