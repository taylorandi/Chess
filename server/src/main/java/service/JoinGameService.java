package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import exception.AlreadyTaken;
import exception.BadRequest;
import exception.Unauthorized;
import model.AuthData;
import request.JoinGameRequest;

public class JoinGameService {
    private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public JoinGameService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public void joinGame(String authToken, JoinGameRequest createGameRequest) throws Exception {
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
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
