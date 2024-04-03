package server;
import handler.*;

import dataAccess.*;
import spark.*;

public class Server {

    private WebSocketHandler webSocketHandler;


    public int run(int desiredPort) {
        Spark.port(desiredPort);
        webSocketHandler = new WebSocketHandler();
        Spark.webSocket("/connect", webSocketHandler);
        try {
            DatabaseManager.createDatabase();
        } catch (Exception e){
            throw new RuntimeException(e);
        }


        Spark.staticFiles.location("web");
        AuthDAO authDao = null;
        GameDAO gameDao = null;
        UserDAO userDao = null;
        try {
            authDao = new SqlAuthDAO();
            userDao = new SqlUserDAO();
            gameDao = new SqlGameDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        

        Spark.delete("/db", new ClearHandler(authDao, gameDao, userDao)::handleRequest);
        Spark.post("/user", new RegisterHandler(authDao, gameDao, userDao)::handleRequest);
        Spark.post("/session", new LoginHandler(authDao, gameDao, userDao)::handleRequest);
        Spark.delete("/session", new LogoutHandler(userDao, authDao, gameDao)::handleRequest);
        Spark.get("/game", new ListGameHandler(userDao, authDao, gameDao)::handleRequest);
        Spark.post("/game", new CreateGameHandler(userDao, authDao, gameDao)::handleRequest);
        Spark.put("/game", new JoinGameHandler(userDao, authDao, gameDao)::handleRequest);

        // Register your endpoints and handle exceptions here.
        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
