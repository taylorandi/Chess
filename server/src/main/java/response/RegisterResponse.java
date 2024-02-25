package response;

public class RegisterResponse {
    private String userName;
    private String authToken;

    public RegisterResponse(String userName, String authToken) {
        this.userName = userName;
        this.authToken = authToken;
    }
}
