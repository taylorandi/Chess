package dataAccess;

import model.UserData;

public class SqlUserDAO implements UserDAO{
    public SqlUserDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String[] createTable = {
            """
        CREATE TABLE IF NOT EXISTS userDao(
        `userName` Varchar(256) not null,
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
    public boolean login(UserData User) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void addUser(UserData user) throws Exception {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
