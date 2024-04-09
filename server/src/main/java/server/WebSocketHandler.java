package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dataAccess.SqlAuthDAO;
import dataAccess.SqlGameDAO;
import exception.Unauthorized;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketmessages.Notification;
import webSocketmessages.userCommands.JoinPlayer;
import webSocketmessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Map;

@WebSocket
public class WebSocketHandler {

    private WebSocketSessions connectionManager;
    private SqlAuthDAO authDAO;
    private SqlGameDAO gameDAO;
    private Session session;

    public void WebsocketHandler(){
        connectionManager = new WebSocketSessions();
        try {
            gameDAO = new SqlGameDAO();
            authDAO = new SqlAuthDAO();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(int gameId, JsonElement message, String authToken) throws IOException {
        Map<String, Session> game;
        game = connectionManager.getSessionsForGame(gameId);
        for (Map.Entry<String, Session> user : game.entrySet()) {
            if (user.getValue().isOpen()) {
                if (user.getKey().equals(authToken)) {
                    user.getValue().getRemote().sendString(message.toString());
                }
            }
        }
    }

    public void broadcastMessage(int gameId, String message, String authToken) throws IOException {
        Map<String, Session> game;
        game = connectionManager.getSessionsForGame(gameId);
        for (Map.Entry<String, Session> user : game.entrySet()) {
            if (user.getValue().isOpen()) {
                if (!user.getKey().equals(authToken)) {
                    user.getValue().getRemote().sendString(message.toString());
                }
            }
        }
    }



    @OnWebSocketConnect
    public void onConnect(Session session){

    }

    @OnWebSocketClose
    public void onClose(Session session){

    }

    @OnWebSocketError
    public void onError(Throwable throwable){

    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Unauthorized {
        String authToken = connectionManager.getAuthToken(session);
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()){
            case JOIN_PLAYER -> joinPlayer(action, session);

        }


    }

    private void joinPlayer(UserGameCommand action, Session session) throws Unauthorized {
        JoinPlayer joinPlayer = new JoinPlayer(action);
        try {
            String user = String.valueOf(authDAO.getUser(joinPlayer.getAuthString()));
            connectionManager.addSessionToGame(joinPlayer.getGameId(), joinPlayer.getAuthString(), session);
            String message = user + ": has joined our glorious game\n";
            broadcastMessage(action.getGameId(), new Gson().toJson(new Notification(message)), joinPlayer.getAuthString());

        } catch (Unauthorized e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
