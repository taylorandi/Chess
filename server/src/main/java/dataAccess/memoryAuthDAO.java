package dataAccess;
import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class memoryAuthDAO implements AuthDAO{
    private Map<String, AuthData> authorizationTokens = new HashMap<>();

    @Override
    public void clear(){
        authorizationTokens.clear();
    }

    @Override
    public void createAcount(UserData user) throws Exception {
        int response;
        if (authorizationTokens.getOrDefault(user.username(), null) == null) {
            AuthData newUser = createToken(user.username());
            authorizationTokens.put(user.email(), newUser);
        }
        else{
            throw new Exception();
        }
    }

        public AuthData createToken(String username){
                return new AuthData(username, UUID.randomUUID().toString());
            }
}
