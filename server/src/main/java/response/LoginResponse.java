package response;

public class LoginResponse {
    private String username;
    private String authToken;

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public LoginResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }


}
