package dataAccess;

import exception.AlreadyTaken;
import exception.Unauthorized;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUserDAO implements UserDAO{
    public SqlUserDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String[] createTable = {
            """
        CREATE TABLE IF NOT EXISTS userDao(
        `username` Varchar(256) not null,
        `password` varchar(256) not null,
        `email` varchar(256) not null,
        primary key (`username`)
        )
        """
    };

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
    public boolean login(UserData user) throws DataAccessException {
        UserData check;
        try {
            check = getUser(user.username());
        } catch (Exception e){
            throw new DataAccessException("couldn't access database");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(user.password(), check.password());
    }

    @Override
    public void clear() throws DataAccessException {
        String sql = "Delete from userDao";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e){
            throw new DataAccessException("Unable to clear userDao");
        }
    }

    @Override
    public void addUser(UserData user) throws Exception {
        UserData check = null;
        try {
            check = getUser(user.username());
        } catch (Exception e){
            throw new Exception(e);
        }
        if(user.username().equals(check.username())){
            throw new AlreadyTaken("ERROR: already taken");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());
        String sql = "INSERT INTO userDao (username, password, email) Values (?, ?, ?)";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, user.email());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException("Could not access database");
        }
    }

    @Override
    public boolean isEmpty() {
        String sql = "Select count(*) from userDao";
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

    private UserData getUser(String username) throws Exception{
        String user = null;
        String password = null;
        String email = null;
        String sql = "Select * from userDao Where userName = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,username);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    user = rs.getNString(1);
                    password = rs.getNString(2);
                    email = rs.getNString(3);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new UserData(user, password, email);
    }
}

