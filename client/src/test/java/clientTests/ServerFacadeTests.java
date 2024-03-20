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
            serverFacade.makeRequest("DELETE", "/db", null, null, null);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Test
    public void validLoginTest() {
        Assertions.assertDoesNotThrow(() -> serverFacade.makeRequest("POST", "/user", null, new UserData("papi", "luca", "123"), LoginResponse.class));
    }

    @Test
    public void invalidLoginUserDoesntExist(){
        Assertions.assertThrows(Exception.class, () -> serverFacade.makeRequest("POST", "/session", null, new UserData("super-man", "lex", "123"), LoginResponse.class));
    }

}
