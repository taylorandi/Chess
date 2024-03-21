package clientTests;

import exception.Unauthorized;
import model.UserData;
import org.junit.jupiter.api.*;
import response.LoginResponse;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        var serverUrl = "http://localhost:" + port;
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new ServerFacade(serverUrl);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    public void clear() {
        try {
            serverFacade.clearServerFacade();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Test
    public void validRegisterTest() {
        String[] user = {"bobby", "joe", "123"};
        Assertions.assertDoesNotThrow(() -> serverFacade.registerServerFacade(user));
    }

    @Test
    public void invalidInput(){
        String[] user = {"bobby", "joe"};
        Assertions.assertThrows(Unauthorized.class, () -> serverFacade.registerServerFacade(user));
    }
    @Test
    public void invalidLoginUserDoesNotExist(){
        String[] user = {"joseph", "jones"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.loginServerFacade(user));
    }

}
