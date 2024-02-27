package response;

public class GameResponse {
    private int gameId;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;

    public GameResponse(int gameId, String whiteUsername, String blackUsername, String gameName) {
        this.gameId = gameId;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
    }
}
