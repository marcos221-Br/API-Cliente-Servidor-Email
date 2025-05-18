package api.tcs.email.exceptions;

public class UnauthorizedException extends RuntimeException{
    
    public UnauthorizedException(String message){
        super(message);
    }
}
