package skhu.ht.hotthink.security.exceptions;

public class JwtException extends RuntimeException{
    public JwtException(String msg){
        super(msg);
    }
}
