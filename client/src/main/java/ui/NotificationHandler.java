package ui;

import org.eclipse.jetty.util.Scanner;

public interface NotificationHandler {
    void notify(Scanner.Notification notification);
}
