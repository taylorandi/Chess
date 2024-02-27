package request;

public class JoinGameRequest {
    private String playerColor;
    private int gameID;

    public JoinGameRequest(String playerColor, int gameID)
    {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }
}
