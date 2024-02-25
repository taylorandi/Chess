package response;

public class LoginResponse {
    private String userName;
    private String authToken;

    public LoginResponse(String userName, String authToken) {
        this.userName = userName;
        this.authToken = authToken;
    }
}
