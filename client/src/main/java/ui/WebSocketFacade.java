package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import org.eclipse.jetty.util.Scanner;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade {

    private static java.net.URI URI = null;
    private NotificationHandler notificationHandler;
    public Session session;

    public WebSocketFacade(String url) throws URISyntaxException, DeploymentException, IOException, ResponseException {
        try {
        url = url.replace("http", "ws");
        this.URI  = new URI(url + "/connect");
        this.notificationHandler = new NotificationHandler();

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, URI);

        //set message handler
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                Scanner.Notification notification = new Gson().fromJson(message, Scanner.Notification.class);
                notificationHandler.notify(notification);
            }
        });
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
