package skhu.ht.hotthink.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "hot-think.security.jwt")
@Getter
@Setter
public class JwtSetting {
    private Integer tokenExpirationTime;
    private String tokenIssuer;
    private String tokenSigningKey;
    private Integer refreshTokenExpTime;
    private String jti;
}
