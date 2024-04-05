package ui;

import chess.ChessGame;
import com.google.gson.JsonElement;

public interface GameHandler {

    public void updateGame(ChessGame game);

    public void printMessage(JsonElement message);
}
