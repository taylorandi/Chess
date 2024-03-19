package exception;

public class ResponseException extends Exception{

    public ResponseException(int i, String error){
        super(error);
    }

}
