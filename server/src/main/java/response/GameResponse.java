package response;

public class GameResponse {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;

    public GameResponse(int gameId, String whiteUsername, String blackUsername, String gameName) {
        this.gameID = gameId;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }
}
