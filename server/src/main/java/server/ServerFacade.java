package server;

import com.google.gson.Gson;
import exception.ResponseException;
import exception.Unauthorized;
import model.UserData;
import request.JoinGameObject;
import request.LoginObject;
import response.CreateGameResponse;
import response.ListGamesResponse;
import response.LoginResponse;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url){
        serverUrl = url;
    }

    public LoginResponse registerServerFacade(String[] parameters) throws ResponseException, Unauthorized {
        if(parameters.length < 3){
            throw new Unauthorized("invalid inputs");
        }
        String username = parameters[0];
        String password = parameters[1];
        String email = parameters[2];
        UserData user = new UserData(username, password, email);
        String method = "POST";
        String path = "/user";
        return makeRequest(method, path, null, user, LoginResponse.class);
    }

    public LoginResponse loginServerFacade(String[] parameters) throws ResponseException, Unauthorized {
        if(parameters.length < 2){
            throw new Unauthorized("invalid inputs");
        }
        String username = parameters[0];
        String password = parameters[1];
        UserData user = new UserData(username, password, null);
        String method = "POST";
        String path = "/session";
        return makeRequest(method, path, null, user, LoginResponse.class);
    }

    public void logoutServerFacade(String authToken) throws ResponseException {
        String method = "DELETE";
        String path = "/session";
        makeRequest(method, path, authToken, null, null);
    }

    public void createGameServerFacade(String[] parameters, String authToken) throws Unauthorized, ResponseException {
        if(parameters.length < 1){
            throw new Unauthorized("invalid inputs");
        }
        String gameName = parameters[0];
        String method = "POST";
        String path = "/game";
        makeRequest(method, path, authToken, new LoginObject(gameName), CreateGameResponse.class);
    }

    private  <T> T makeRequest(String method, String path, String authToken,  Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if(authToken != null){
                http.addRequestProperty("authorization", authToken);
            }
            writeRequestBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeRequestBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    public ListGamesResponse listGameServerFacade(String authToken) throws ResponseException {
        String method = "GET";
        String path = "/game";
        return makeRequest(method, path, authToken, null, ListGamesResponse.class);
    }

    public void joinGameServerFacade(String[] parameters, int game, String authToken) throws ResponseException, Unauthorized {
        if(parameters.length < 2){
            throw new Unauthorized("invalid inputs");
        }
        String method = "PUT";
        String path = "/game";
        String playerColor = parameters[0];
        JoinGameObject join = new JoinGameObject(playerColor, game);
        makeRequest(method, path, authToken, join, null);
    }

    public void joinGameObserverServerFacade(int game, String authToken) throws ResponseException {
        String method = "PUT";
        String path = "/game";
        JoinGameObject join = new JoinGameObject(null, game);
        makeRequest(method, path, authToken, join, null);
    }

    public void clearServerFacade() throws ResponseException {
        String method = "DELETE";
        String path = "/db";
        makeRequest(method, path, null, null, null);
    }

}
