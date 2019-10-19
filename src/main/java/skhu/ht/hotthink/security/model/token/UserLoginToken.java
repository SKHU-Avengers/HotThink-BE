package skhu.ht.hotthink.security.model.token;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserLoginToken extends UsernamePasswordAuthenticationToken {
    @Getter
    private SimpleGrantedAuthority auth;

    public UserLoginToken(Object principal, Object credentials, SimpleGrantedAuthority auth) {
        super(principal, credentials);
        this.auth = auth;
    }
}
