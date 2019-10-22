package skhu.ht.hotthink.security.model.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;
import skhu.ht.hotthink.config.JwtSetting;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenFactory {
    private final JwtSetting setting;

    @Autowired
    public JwtTokenFactory(JwtSetting setting){
        this.setting = setting;
    }

    public AccessJwtToken createAccessJwtToken(UserAuthenticationModel user) {
        if (StringUtils.isBlank(user.getEmail())) throw new IllegalArgumentException("유저 이멜 없이 토큰 만들 수 없음");
        if (user.getAuth() == null) throw new IllegalArgumentException("아무 권한도 없음 안됨");

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("auth", user.getAuth().getAuthority());

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(setting.getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(setting.getTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, setting.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserAuthenticationModel user) {
        if (StringUtils.isBlank(user.getEmail())) throw new IllegalArgumentException("유저 이멜 없이 토큰 만들 수 없음");

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("auth", "Hello_Refresh_Token");

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(setting.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(setting.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, setting.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }
}
