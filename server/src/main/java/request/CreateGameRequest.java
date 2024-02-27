package request;

public class CreateGameRequest {
    private String gameName;

    public CreateGameRequest(String authToken) {
        this.gameName = authToken;
    }

    public String getGameName() {
        return gameName;
    }
}
