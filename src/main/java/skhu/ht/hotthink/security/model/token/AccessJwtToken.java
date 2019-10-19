package skhu.ht.hotthink.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessJwtToken implements JwtToken {

    private final String rawToken;
    @Getter @JsonIgnore private Claims claims;

    @Override
    public String getToken() {
        return this.rawToken;
    }

}
