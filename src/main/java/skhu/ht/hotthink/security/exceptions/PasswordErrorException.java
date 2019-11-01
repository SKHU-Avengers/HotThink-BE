package skhu.ht.hotthink.security.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class PasswordErrorException extends BadCredentialsException {
    private static final long serialVersionUID = -8985033645561254667L;

    public PasswordErrorException(String msg) {
        super(msg);
    }
}
