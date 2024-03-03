package dataAccess;

import model.UserData;

public interface UserDAO {
    boolean login(UserData User) throws DataAccessException;

    void clear() throws DataAccessException;

    void addUser(UserData user) throws Exception;

    boolean isEmpty();
}
