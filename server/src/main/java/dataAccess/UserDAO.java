package dataAccess;

import model.UserData;

public interface UserDAO {
    void clear();

    void addUser(UserData user) throws Exception;
}
