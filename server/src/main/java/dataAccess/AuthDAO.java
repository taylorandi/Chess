package dataAccess;

import model.UserData;

public interface AuthDAO {

    public void clear();

    public void createAcount(UserData user) throws Exception;

}
