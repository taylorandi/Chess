package dataAccessTests;

import dataAccess.*;
import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import response.GameResponse;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseGameDaoTests {
    private AuthDAO authDAO = null;
    private GameDAO gameDao = null;
    private UserDAO userDao = null;

    @BeforeEach
    public void init(){
        try{
            DatabaseManager.createDatabase();
            authDAO = new SqlAuthDAO();
            gameDao = new SqlGameDAO();
            userDao = new SqlUserDAO();
            authDAO.clear();
            gameDao.clear();
            userDao.clear();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void clearTest(){
        try {
            String auth = authDAO.createAccount(new UserData("Lorie Adams", "Antikleia Merope", "joe.com")).authToken();
            gameDao.createGame("Fulgora Remus");
            gameDao.clear();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(gameDao.isEmpty());
    }

    @Test
    public void goodCreate(){
        int game = 0;
        try{
            game = gameDao.createGame("Antigone Mordred");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertNotEquals(game, 0);
    }

    @Test
    public void noGameName(){
        Assertions.assertThrows(BadRequest.class, () -> gameDao.createGame(null));
    }

    @Test
    public void goodJoin(){
        int gameID = 0;
        try{
            gameID = gameDao.createGame("Hippolyte Cupido");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        int finalGameID = gameID;
        Assertions.assertDoesNotThrow(() -> gameDao.joinGame("WHITE", finalGameID, new AuthData("Alkestis Maira", "Eudora Athene")));
    }

    @Test
    public void badJoin(){
        int gameID = 0;
        try{
            gameID = gameDao.createGame("Hippolyte Cupido");
            gameDao.joinGame("WHITE", gameID, new AuthData("Alkestis Maira", "Eudora Athene"));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        int finalGameID = gameID;
        Assertions.assertThrows(AlreadyTaken.class, () -> gameDao.joinGame("WHITE", finalGameID, new AuthData("Céfiro Leander", "Eudora Athene")));
    }

    @Test
    public void getGameList() throws DataAccessException {
        ArrayList<GameResponse> gameResponses = new ArrayList<>();
        int gameID;
        try{
            gameID = gameDao.createGame("Hippolyte Cupido");
            gameDao.joinGame("WHITE", gameID, new AuthData("Alkestis Maira", "Eudora Athene"));
            gameResponses = gameDao.getGames();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        GameResponse gameResponse = gameResponses.get(0);

        Assertions.assertEquals(gameResponse.getGameID(), gameID);
    }

    @Test
    public void noGames() throws DataAccessException {
        Assertions.assertTrue(gameDao.getGames().isEmpty());
    }

    @Test
    public void goodGetGame(){
        int gameID;
        GameData gameResponse;
        try{
            gameID = gameDao.createGame("Quirinus Brontes");
            gameDao.joinGame("WHITE", gameID, new AuthData("Bláithín Alkeides", "Eudora Athene"));
            gameResponse = gameDao.getGame(gameID);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(gameResponse.whiteUserName(), "Bláithín Alkeides");
    }

    @Test
    public void badGet(){
        int gameID;
        try{
            gameID = gameDao.createGame("Quirinus Brontes");
            gameDao.joinGame("WHITE", gameID, new AuthData("Bláithín Alkeides", "Eudora Athene"));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
       Assertions.assertThrows(BadRequest.class, ()-> gameDao.getGame(5));
    }

    @Test
    public void goodVerify() throws DataAccessException {
        int gameID;
        try{
            gameID = gameDao.createGame("Quirinus Brontes");
            gameDao.joinGame("WHITE", gameID, new AuthData("Bláithín Alkeides", "Eudora Athene"));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(gameDao.verify(gameID));
    }

    @Test
    public void verifyFalse() throws DataAccessException {
        Assertions.assertFalse(gameDao.verify(6));
    }
}
