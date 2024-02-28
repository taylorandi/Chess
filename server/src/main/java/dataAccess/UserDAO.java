package dataAccess;

import model.UserData;

public interface UserDAO {
    boolean login(UserData User);

    void clear();

    void addUser(UserData user) throws Exception;

    boolean isEmpty();
}
