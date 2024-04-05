package ui;

import exception.ResponseException;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint implements MessageHandler {

    private static java.net.URI URI = null;
    public Session session;
    private GameHandler gameHandler;

    public WebSocketFacade(String url, GameHandler gameHandler) throws URISyntaxException, DeploymentException, IOException, ResponseException {
        try {
            url = url.replace("http", "ws");
            this.URI = new URI(url + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, URI);
            this.gameHandler = gameHandler;
            //set message handler
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }




    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }


}
