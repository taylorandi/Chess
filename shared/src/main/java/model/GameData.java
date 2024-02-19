package model;

import chess.ChessGame;

public record GameData() {
    private static int gameId;
    private static String whiteUserName;
    private static String blackUserName;
    private static String gameName;
    private static ChessGame game;
}
