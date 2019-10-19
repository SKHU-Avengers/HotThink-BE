package skhu.ht.hotthink.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;
import skhu.ht.hotthink.security.model.token.JwtAuthenticationToken;
import skhu.ht.hotthink.security.model.token.RawAccessJwtToken;
import skhu.ht.hotthink.config.JwtSetting;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtSetting jwtSetting;

    @Autowired
    public JwtAuthenticationProvider(JwtSetting jwtSettings) {
        this.jwtSetting = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken)authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSetting.getTokenSigningKey());

        UserAuthenticationModel user = UserAuthenticationModel.builder()
                .email(jwsClaims.getBody().getSubject())
                .pw(null)
                .auth(new SimpleGrantedAuthority(jwsClaims.getBody().get("auth", String.class)))
                .build();
        return new JwtAuthenticationToken(user, user.getAuth());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
