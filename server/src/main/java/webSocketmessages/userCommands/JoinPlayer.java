package webSocketmessages.userCommands;

import chess.ChessGame;


public class JoinPlayer extends UserGameCommand {

   private Integer gameID;
   private ChessGame.TeamColor teamColor;
   private CommandType commandType;

    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor teamColor) {
        super(authToken);
        this.gameID = gameID;
        this.teamColor = teamColor;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    public JoinPlayer(UserGameCommand userGameCommand){
        super(userGameCommand.getAuthString());
        this.teamColor = userGameCommand.getTeamColor();
        this.gameID = userGameCommand.getGameId();
        this.commandType = CommandType.JOIN_PLAYER;
    }

}
