package dataAccess;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class memoryAuthDAO implements AuthDAO{
    private HashMap<String, AuthData> authorizationTokens = new HashMap<>();

    @Override
    public void clear(){
        authorizationTokens.clear();
    }

    @Override
    public AuthData createAcount(UserData user) {
            AuthData newUser = createToken(user.username());
            authorizationTokens.put(newUser.authToken(), newUser);
            return newUser;
    }

    @Override
    public void logoutUser(String authToken) throws Unauthorized {
        try {
            if(authorizationTokens.containsKey(authToken)) {
                authorizationTokens.remove(authToken);
            }
            else {
                throw new Unauthorized("ERROR: unauthorized");
            }
        } catch (Exception e){
            throw new Unauthorized("ERROR: unauthorized");
        }
    }
    @Override
    public AuthData getUser(String authToken){
        return authorizationTokens.get(authToken);
    }

    @Override
    public boolean verify(String authToken){
        return authorizationTokens.getOrDefault(authToken, null) != null;
    }

    @Override
    public boolean isEmpty(){
        return authorizationTokens.isEmpty();
    }

        public AuthData createToken(String username){
                return new AuthData(username, UUID.randomUUID().toString());
            }
}
