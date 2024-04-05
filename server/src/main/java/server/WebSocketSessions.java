package server;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;

;

public class WebSocketSessions {
    private Map<Integer, Map<String, Session>> sessions;

    public void addSessionToGame(int gameID, String authToken, Session session){
        Map<String, Session> current = null;
        current.put(authToken, session);
        sessions.put(gameID, current);
    }

    public void removeSession(int gameID, String authToken, Session session){
        Map<String, Session> current;
        current = sessions.get(gameID);
        current.remove(authToken);
    }

    public void removeSession(Session session){
        String auth = null;
        for(Map<String, Session> current : sessions.values() ){
            for (String key : current.keySet()){
                if(current.get(key).equals(session)){
                    auth = key;
                }
            }
            current.remove(auth);

        }
    }

    public Map<String, Session> getSessionsForGame(int gameId){
        return sessions.get(gameId);
    }


}
