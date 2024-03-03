package dataAccess;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.UUID;

public class memoryAuthDAO implements AuthDAO{
    private HashMap<String, AuthData> authorizationTokens = new HashMap<>();

    @Override
    public void clear(){
        authorizationTokens.clear();
    }

    @Override
    public AuthData createAccount(UserData user) {
            AuthData newUser = createToken(user.username());
            authorizationTokens.put(newUser.authToken(), newUser);
            return newUser;
    }

    @Override
    public Executable logoutUser(String authToken) throws Unauthorized {
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
        return null;
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
