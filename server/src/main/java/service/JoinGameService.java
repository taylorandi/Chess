package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.AlreadyTaken;
import exception.BadRequest;
import exception.Unauthorized;
import model.AuthData;
import request.JoinGameRequest;
import spark.Request;

public class JoinGameService {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public JoinGameService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public void joinGame(Request request) throws Unauthorized, BadRequest, AlreadyTaken {
        String authToken = request.headers("Authorization");
        if (authToken == null || !authDao.verify(authToken)) {
            throw new Unauthorized("ERROR: unauthorized");
        }
        JoinGameRequest createGameRequest = new Gson().fromJson(request.body(), JoinGameRequest.class);
        String playerColor = createGameRequest.getPlayerColor();
        int gameId = createGameRequest.getGameID();
        AuthData player = authDao.getUser(authToken);
        if(gameId == 0){
            throw new BadRequest("ERROR: bad request");
        }
        if(playerColor == null && gameDao.verify(gameId)){
            return;
        }
        try{
            gameDao.joinGame(playerColor, gameId, player);
        } catch (BadRequest e){
            throw new BadRequest("ERROR: bad request");
        }
        catch (AlreadyTaken e){
            throw new AlreadyTaken("ERROR: already taken");
        }
    }
}
