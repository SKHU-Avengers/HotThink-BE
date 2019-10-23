package skhu.ht.hotthink.security.auth.login;

import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import skhu.ht.hotthink.api.user.service.UserService;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;
import skhu.ht.hotthink.security.model.token.UserLoginToken;

@Component
@Slf4j
public class LoginAuthenticationProvier implements AuthenticationProvider {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public LoginAuthenticationProvier(final UserService userService, final BCryptPasswordEncoder encoder){
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("로그인 Provider 진입");
        Assert.notNull(authentication, "인증 없으면 안됨");
        UserAuthenticationModel user = userService.findUserByEmailAndPw((String)authentication.getPrincipal(), (String)authentication.getCredentials());
        return new UserLoginToken(user, null, user.getAuth());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
