package dataAccess;

import exception.Unauthorized;
import model.AuthData;
import model.UserData;

public interface AuthDAO {

    public void clear();

    public AuthData createAcount(UserData user) throws Exception;

    void logoutUser(String logout) throws Unauthorized;

    AuthData getUser(String authToken);

    boolean verify(String authToken);

}
