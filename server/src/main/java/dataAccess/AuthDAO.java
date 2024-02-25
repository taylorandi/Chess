package dataAccess;

import model.UserData;

public interface AuthDAO {

    public void clear();

    public String createAcount(UserData user) throws Exception;

}
