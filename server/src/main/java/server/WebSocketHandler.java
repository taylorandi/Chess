package server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebSocketHandler {

    private final WebSocketSessions connectionManager = new WebSocketSessions();

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
