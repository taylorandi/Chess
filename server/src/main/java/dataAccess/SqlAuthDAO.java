package dataAccess;

import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.function.Executable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlAuthDAO implements  AuthDAO{
    private final String[] createTable = {
        """
        CREATE TABLE IF NOT EXISTS authDao(
        `authToken` Varchar(256) not null,
        `username` varchar(256) not null,
        primary key (`authToken`)
        )
        """
    };
    public SqlAuthDAO() throws Exception{
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException{
        try (var connection = DatabaseManager.getConnection()){
            for(var statement : createTable) {
                try (var statements = connection.prepareStatement(statement)){
                    statements.executeUpdate();
                }
            }
        }catch (Exception e){
            throw new DataAccessException("Unable to configure Database");
        }
    }

    @Override
    public void clear() throws DataAccessException {
        String sql = "Delete from authDao";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e){
            throw new DataAccessException("Unable to clear authDao");
        }
    }

    @Override
    public AuthData createAccount(UserData user) throws Exception {
        if(user.username() == null){
            throw new Unauthorized("Error: unauthorized");
        }
        String sql = "INSERT INTO authDao (authToken, username) Values (?, ?)";
        String authToken = createToken();
        String username = user.username();
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, authToken);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException("Could not access database");
        }
        return new AuthData(user.username(), authToken);
    }

    @Override
    public Executable logoutUser(String authToken) throws Exception {
        if(!verify(authToken)){
            throw new Unauthorized("ERROR: unauthorized");
        }
        String sql = "DELETE from authDao Where authToken = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e){
            throw new DataAccessException("Could not log user out");
        }
        return null;
    }

    @Override
    public AuthData getUser(String authToken) throws Unauthorized {
        AuthData authData = getPerson(authToken);
        if(authData.username() == null){
            throw new Unauthorized("ERROR: unauthorized");
        }
            return authData;
    }

    @Override
    public boolean verify(String authToken) {
        AuthData authData = getPerson(authToken);
        return (authData.username() != null);
    }

    @Override
    public boolean isEmpty() {
        String sql = "Select count(*) from authDao";
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

    private AuthData getPerson(String authToken){
        String sql = "Select * from authDao Where authToken = ?";
        String username = null;
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,authToken);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    username = rs.getNString(2);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new AuthData(username, authToken);
        }

    private String createToken(){
        return UUID.randomUUID().toString();
    }

}
