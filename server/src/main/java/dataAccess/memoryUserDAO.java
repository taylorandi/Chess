package dataAccess;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class memoryUserDAO implements UserDAO{

    private Map<String, UserData> users = new HashMap<>();

    @Override
    public void clear(){
            users.clear();
    }

    @Override
    public void addUser(UserData user) throws Exception {
        if(users.getOrDefault(user.username(), null) == null){
            users.put(user.username(), user);
        }
        else {
            throw new Exception();
        }
    }
}
