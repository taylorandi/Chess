package response;

import java.util.ArrayList;

public class ListGamesResponse {
    private ArrayList<GameResponse> games;

    public ListGamesResponse(ArrayList<GameResponse> games) {
        this.games = games;
    }

    public ArrayList<GameResponse> getGames() {
        return games;
    }
}
