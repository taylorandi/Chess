package clientTests;

import exception.Unauthorized;
import org.junit.jupiter.api.*;
import response.CreateGameResponse;
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
    public void alreadyTaken(){
        String[] user = {"bobby", "joe", "123"};
        try {
            serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        Assertions.assertThrows(Exception.class, () -> serverFacade.registerServerFacade(user));
    }

    @Test
    public void validLogin(){
        String[] user = {"bobby", "joe", "123"};
        try {
            serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        String[] login = {"bobby", "joe"};
        Assertions.assertDoesNotThrow(() -> serverFacade.loginServerFacade(login));

    }

    @Test
    public void invalidLoginUserDoesNotExist(){
        String[] user = {"joseph", "jones"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.loginServerFacade(user));
    }

    @Test
    public void invalidLoginInput(){
        String[] user = {"joseph"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.loginServerFacade(user));
    }

    @Test
    public void successfulLogout(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
           token = serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        Assertions.assertDoesNotThrow(() -> serverFacade.logoutServerFacade(authToken));
    }

    @Test
    public void invalidAuthTokenLogout(){
        Assertions.assertThrows(Exception.class, () -> serverFacade.logoutServerFacade("4"));
    }

    @Test
    public void invalidPassword(){
        String[] user = {"bobby", "joe", "123"};
        try {
            serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        String[] badPass = {"bobby", "kingston"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.loginServerFacade(badPass));
    }

    @Test
    public void goodCreate(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        String[] game = {"myGame"};
        Assertions.assertDoesNotThrow(() -> serverFacade.createGameServerFacade(game, authToken));
    }

    @Test
    public void noGameName(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        Assertions.assertThrows(Exception.class, () -> serverFacade.createGameServerFacade(null, authToken));
    }

    @Test
    public void invalidAuthTokenBadCreate(){
        String[] game = {"myGame"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.createGameServerFacade(game, "noauth"));
    }

    @Test
    public void successfulGetGames(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        Assertions.assertDoesNotThrow(() -> serverFacade.listGameServerFacade(authToken));
    }

    @Test
    public void badAuthNoList(){
        Assertions.assertThrows(Exception.class, () -> serverFacade.listGameServerFacade("papa"));
    }

    @Test
    public void validJoin(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        String[] game = {"myGame"};
        CreateGameResponse createGameResponse = null;
        try {
            createGameResponse = serverFacade.createGameServerFacade(game, authToken);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        String[] colors = {"black" , "0"};
        CreateGameResponse finalCreateGameResponse = createGameResponse;
        Assertions.assertDoesNotThrow( () -> serverFacade.joinGameServerFacade(colors, finalCreateGameResponse.getGameID(), authToken));
    }

    @Test
    public void invalidJoin(){
        String[] colors = {"black" , "1"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGameServerFacade(colors, 1, "3"));
    }

    @Test
    public void invalidGameID() {
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        String[] game = {"myGame"};
        try {
            serverFacade.createGameServerFacade(game, authToken);
            serverFacade.listGameServerFacade(authToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String[] colors = {"black", "1"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGameServerFacade(colors, 1234, authToken));
    }

    @Test
    public void joinObserver(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        String[] game = {"myGame"};
        CreateGameResponse createGameResponse = null;
        try {
            createGameResponse = serverFacade.createGameServerFacade(game, authToken);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        CreateGameResponse finalCreateGameResponse = createGameResponse;
        String[] parameters = {"0"};
        Assertions.assertDoesNotThrow( () -> serverFacade.joinGameObserverServerFacade(parameters, finalCreateGameResponse.getGameID(), authToken));
    }

    @Test
    public void invalidGameIDObserver(){
        String[] user = {"bobby", "joe", "123"};
        LoginResponse token = null;
        try {
            token = serverFacade.registerServerFacade(user);
         } catch (Exception e){
            System.out.println(e.getMessage());
        }
        assert token != null;
        final String authToken = token.getAuthToken();
        String[] game = {"myGame"};
        CreateGameResponse createGameResponse = null;
        try {
             createGameResponse = serverFacade.createGameServerFacade(game, authToken);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        String[] colors = {"black" , "1"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGameServerFacade(colors, 7, authToken));
    }

    @Test
    public void invalidAuthJoin(){
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGameObserverServerFacade(new String[]{"5"}, 5, "mom"));
    }

    @Test
    public void invalidInputs(){
        Assertions.assertThrows(Exception.class, () -> serverFacade.joinGameServerFacade(null, 5, "mom"));
    }

    @Test
    public void noUsername(){
        String[] user = {null, "joe", "paul"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.registerServerFacade(user));
    }

    @Test
    public void noPassword(){
        String[] user = {"joe", null, "paul"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.registerServerFacade(user));
    }

    @Test
    public void noMethodsInTheClient(){
        String[] badUser = {"POST", "DELETE", "PUT", "GET"};
        Assertions.assertThrows(Exception.class, () -> serverFacade.registerServerFacade(badUser));
    }

    @Test
    public void successfulClear(){
        String[] user = {"bobby", "joe", "123"};
        try {
            serverFacade.registerServerFacade(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        Assertions.assertDoesNotThrow(() -> serverFacade.clearServerFacade());
    }

}
