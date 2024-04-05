package server;

import com.google.gson.JsonElement;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Map;

@WebSocket
public class WebSocketHandler {

    private WebSocketSessions connectionManager;
    private Session session;

    public void WebsocketHandler(){
        connectionManager = new WebSocketSessions();
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

    public void broadcastMessage(int gameId, JsonElement message, String authToken) throws IOException {
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

    @OnWebSocketMessage
    public void onError(Session session, String message){

    }

}
