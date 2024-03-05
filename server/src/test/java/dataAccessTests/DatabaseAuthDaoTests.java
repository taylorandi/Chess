package dataAccessTests;

import dataAccess.*;
import exception.BadRequest;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseAuthDaoTests {
    private AuthDAO authDAO = null;
    private GameDAO gameDao = new memoryGameDAO();
    private UserDAO userDao = new memoryUserDAO();

    @BeforeEach
    public void init(){
        try{
            DatabaseManager.createDatabase();
            authDAO = new SqlAuthDAO();
            authDAO.clear();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Test
    public void clearTable(){
        try {
            authDAO.createAccount(new UserData("Ferdo Maša", "1234", "joe.com"));
            authDAO.clear();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        ;
        Assertions.assertTrue(authDAO.isEmpty());
    }

    @Test
    public void LoginUser() throws Exception {
        String authToken;
        authToken =  authDAO.createAccount(new UserData("Ferdo Maša", "1234", "joe.com")).authToken();
        Assertions.assertTrue(authDAO.verify(authToken));
    }
    @Test
    public void unauthorizedLogin(){
        Assertions.assertThrows(Unauthorized.class, () -> authDAO.createAccount(new UserData(null, "1234", "joe.com")));
    }

    @Test
    public void successfulLogout() {
        AuthData authData;
        try {
            authData = authDAO.createAccount(new UserData("Raven Laila", "Abiodun Spartak", "jpe.com"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertDoesNotThrow(() -> authDAO.logoutUser(String.valueOf(authData.authToken())));
    }

    @Test
    public void invalidLogout() {
        AuthData authData;
        try {
            authData = authDAO.createAccount(new UserData("Barlaam Langdon", "Abiodun Spartak", "jpe.com"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(Unauthorized.class, () -> authDAO.logoutUser("Stepan Sandip"));
    }

    @Test
    public void getUser() throws BadRequest, Unauthorized {
        AuthData authData;
        try {
            authData = authDAO.createAccount(new UserData("Berenhard Marcela", "Abiodun Spartak", "jpe.com"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(authDAO.getUser(authData.authToken()).username(), authData.username());
    }

    @Test
    public void unauthorizedGetUser(){
        AuthData authData;
        try {
            authData = authDAO.createAccount(new UserData("Nargis Ganesh", "Abiodun Spartak", "jpe.com"));
            authDAO.logoutUser(authData.authToken());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(Unauthorized.class, () -> authDAO.getUser(authData.authToken()));
    }

    @Test
    public void goodVerify(){
        AuthData authData;
        try {
            authData = authDAO.createAccount(new UserData("Tina Khumbo", "Abiodun Spartak", "jpe.com"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertTrue (authDAO.verify(authData.authToken()));
    }

    @Test
    public void invalidVerify(){
        AuthData authData;
        try {
            authData = authDAO.createAccount(new UserData("Filip Sudarshan", "Abiodun Spartak", "jpe.com"));
            authDAO.logoutUser(authData.authToken());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Assertions.assertFalse (authDAO.verify("Chloe Käthe"));
    }

    }


