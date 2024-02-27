package server;
import handler.*;

import dataAccess.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        AuthDAO authDao = new memoryAuthDAO();
        GameDAO gameDao = new memoryGameDAO();
        UserDAO userDao = new memoryUserDAO();

        Spark.delete("/db", new ClearHandler(authDao, gameDao, userDao)::handleRequest);
        Spark.post("/user", new RegisterHandler(authDao, gameDao, userDao)::handleRequest);
        Spark.post("/session", new LoginHandler(authDao, gameDao, userDao)::handleRequest);
        Spark.delete("/session", new LogoutHandler(userDao, authDao, gameDao)::handleRequest);
        Spark.get("/game", new ListGameHandler(userDao, authDao, gameDao)::handleRequest);

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
