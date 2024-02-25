package dataAccess;
import exception.Unauthorized;
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
    public String createAcount(UserData user) throws Exception {
        int response;
        if (authorizationTokens.getOrDefault(user.username(), null) == null) {
            AuthData newUser = createToken(user.username());
            authorizationTokens.put(newUser.AuthToken(), newUser);
            return newUser.AuthToken();
        }
        else{
            throw new Exception();
        }
    }

    public void logoutUser(AuthData user) throws Unauthorized {
        try {
            authorizationTokens.remove(user.AuthToken());
        } catch (Exception e){
            throw new Unauthorized("unauthorized");
        }
    }

        public AuthData createToken(String username){
                return new AuthData(username, UUID.randomUUID().toString());
            }
}
