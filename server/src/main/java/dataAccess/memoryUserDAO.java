package dataAccess;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class memoryUserDAO implements UserDAO{

    private static HashMap<String, UserData> users = new HashMap<>();

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

    @Override
    public boolean login(UserData user){
        if(users.getOrDefault(user.username(), null) != null){
            UserData verify = users.get(user.username());
            return verify.password().equals(user.password());
        }
        else{
            return false;
        }
    }

    @Override
    public boolean isEmpty(){
        return users.isEmpty();
    }
}
