package dataAccess;

import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.function.Executable;

public interface AuthDAO {

    public void clear() throws DataAccessException;

    public AuthData createAccount(UserData user) throws Exception;

    Executable logoutUser(String logout) throws Exception;

    AuthData getUser(String authToken) throws Unauthorized;

    boolean verify(String authToken);

    boolean isEmpty();
}
