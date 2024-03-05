package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlGameDAO implements GameDAO{

    public SqlGameDAO() throws DataAccessException {
        configureDatabase();
    }
    private final String[] createTable = {
            """
        CREATE TABLE IF NOT EXISTS gameDao(
        `gameID` integer not null AUTO_INCREMENT,
        `whiteUsername` Varchar(256),
        `blackUserName` varchar(256),
        `gameName` varchar(256) not null,
        `game` TEXT not null,
        primary key (`gameID`)
        )
        """
    };

    private void configureDatabase() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            for (var statement : createTable) {
                try (var statements = connection.prepareStatement(statement)) {
                    statements.executeUpdate();
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }catch (Exception e){
            throw new DataAccessException("Unable to configure Database");
        }
    }


    @Override
    public void clear() throws DataAccessException {
        String sql = "Delete from gameDao";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e){
            throw new DataAccessException("Unable to clear gameDao");
        }
    }

    @Override
    public ArrayList getGames() {
        return null;
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        String sql = "INSERT INTO gameDao (gameName, game) Values (?, ?)";
        ChessGame game = new ChessGame();
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, new Gson().toJson(game));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException("Could not access database");
        }
        return getGameID(gameName);
    }



    @Override
    public void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken {

    }

    @Override
    public boolean verify(int gameId) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        String sql = "Select count(*) from gameDao";
        int rows = 0;
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                ResultSet rs = preparedStatement.executeQuery(sql);
                while (rs.next()){
                    rows = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return switch (rows) {
            case 0 -> true;
            default -> false;
        };
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }
    private int getGameID(String gameName) {
        int gameId = 0;
        String sql = "Select gameID from gameDao Where gameName = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, gameName);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                    gameId = rs.getInt(1);
                }
                return gameId;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
