package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {

    public void clear();

    public String createAcount(UserData user) throws Exception;

    void logoutUser(AuthData logout);
}
