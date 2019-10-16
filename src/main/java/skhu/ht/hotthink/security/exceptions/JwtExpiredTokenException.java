package skhu.ht.hotthink.security.exceptions;

import org.springframework.security.core.AuthenticationException;
import skhu.ht.hotthink.security.model.token.JwtToken;

public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -8985033645561254667L;

    private JwtToken token;

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token(){
        return this.token.getToken();
    }
}
