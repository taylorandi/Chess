package exception;

public class AlreadyTaken extends Exception{
    public AlreadyTaken(String error){
        super(error);
    }
}
