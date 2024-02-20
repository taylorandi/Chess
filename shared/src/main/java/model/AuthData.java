package model;
import java.util.UUID;


public record AuthData( String AuthToken, String userName) {
    public AuthData createNewAuthToken(String username){
        return new AuthData(username, UUID.randomUUID().toString());
    }
}