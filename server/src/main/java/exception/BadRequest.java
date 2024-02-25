package exception;

public class BadRequest extends Exception {
    public BadRequest(String error){
        super(error);
    }
}
