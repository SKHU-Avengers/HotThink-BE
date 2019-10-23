package skhu.ht.hotthink.security.model.token;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken implements JwtToken {
    @Getter private Jws<Claims> claims;

    public String getJti(){
        return claims.getBody().getId();
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }

    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        String auth = claims.getBody().get("auth", String.class);
        if (auth == null || !auth.equals("Hello_Refresh_Token")) return Optional.empty();

        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }
}
