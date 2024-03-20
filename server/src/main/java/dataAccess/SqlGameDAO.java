package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.AlreadyTaken;
import exception.BadRequest;
import model.AuthData;
import model.GameData;
import response.GameResponse;
import response.ListGamesResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlGameDAO implements GameDAO {

    public SqlGameDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String[] createTable = {
            """
        CREATE TABLE IF NOT EXISTS gameDao(
        `gameID` integer not null AUTO_INCREMENT,
        `whiteUsername` Varchar(256),
        `blackUsername` varchar(256),
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new DataAccessException("Unable to clear gameDao");
        }
    }

    @Override
    public ArrayList getGames() throws DataAccessException {
        int gameId;
        String whiteUsername;
        String blackUsername;
        String gameName;
        ArrayList<GameResponse> games = new ArrayList<>();
        String sql = "Select gameID, whiteUsername, blackUsername, gameName From gameDao";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                ResultSet rs = preparedStatement.executeQuery(sql);
                     while (rs.next()) {
                         gameId = rs.getInt(1);
                         whiteUsername = rs.getString(2);
                         blackUsername = rs.getString(3);
                         gameName = rs.getString(4);
                         games.add(new GameResponse(gameId, whiteUsername, blackUsername, gameName));
                    }
                 return games;
                }
             } catch (Exception e) {
            throw new DataAccessException("could not fetch data");
        }
    }

    @Override
    public int createGame(String gameName) throws DataAccessException, BadRequest {
        if(gameName == null){
            throw new BadRequest("ERROR: bad request");
        }
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
    public void joinGame(String playerColor, int gameId, AuthData player) throws BadRequest, AlreadyTaken, DataAccessException {
        GameData game = getGame(gameId);
        String sql;
        if(playerColor.equals("white") || playerColor.equals("WHITE")){
            sql = "Update gameDao set whiteUsername = ? Where gameId = ?";
            if(game.whiteUserName() != null){
                throw new AlreadyTaken("ERROR: already taken");
            }
        }
        else {
            sql = "Update gameDao Set blackUsername = ? Where gameId = ?";
            if(game.blackUserName() != null){
                throw new AlreadyTaken("ERROR: already taken");
            }
        }
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, player.username());
                preparedStatement.setInt(2, gameId);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e){
            throw new DataAccessException("Unable to update game");
        }
    }

    @Override
    public boolean verify(int gameId) throws DataAccessException {
        String game = null;
        String sql = "Select gameName from gameDao Where gameId = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, gameId);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    game = rs.getString(1);
                }
                return (game != null);
            }
            } catch (Exception e){
                throw new DataAccessException("could not fetch data");
            }
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
    public GameData getGame(int gameID) throws DataAccessException, BadRequest {
        if(!verify(gameID)){
            throw new BadRequest("ERROR: bad request");
        }
        String whiteUsername = null;
        String blackUsername = null;
        String gameName = null;
        String game = null;
        ArrayList<GameResponse> games = null;
        String sql = "Select * from gameDao Where gameID = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, gameID);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    gameID = rs.getInt(1);
                    whiteUsername = rs.getString(2);
                    blackUsername = rs.getString(3);
                    gameName = rs.getString(4);
                    game = rs.getString(5);
                }
                return new GameData(gameID, whiteUsername, blackUsername, gameName, new Gson().fromJson(game, ChessGame.class));

            }
        } catch (Exception e) {
            throw new DataAccessException("could not fetch data");
        }

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
