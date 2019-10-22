package skhu.ht.hotthink.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.security.auth.jwt.BloomFilterTokenVerifier;
import skhu.ht.hotthink.security.auth.jwt.JwtTokenExtractor;
import skhu.ht.hotthink.config.JwtSetting;
import skhu.ht.hotthink.security.exceptions.JwtException;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;
import skhu.ht.hotthink.security.model.token.JwtToken;
import skhu.ht.hotthink.security.model.token.JwtTokenFactory;
import skhu.ht.hotthink.security.model.token.RawAccessJwtToken;
import skhu.ht.hotthink.security.model.token.RefreshToken;

@Service
public class TokenRefreshService {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSetting jwtSettings;
    @Autowired private BloomFilterTokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtTokenExtractor") private JwtTokenExtractor tokenExtractor;

    public JwtToken tokenRefresh(String header){
        String tokenPayload = tokenExtractor.extract(header);
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken,
                jwtSettings.getTokenSigningKey()).orElseThrow(() -> new JwtException("토큰 유효하지 않음"));
        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti))  throw new JwtException("토큰 유효하지 않음");
        String subject = refreshToken.getSubject();
        User entity = userRepository.findUserByEmail(subject);
        if(entity == null) throw new UsernameNotFoundException("유저 없음");

        UserAuthenticationModel user = UserAuthenticationModel.builder()
                .email(entity.getEmail())
                .auth(new SimpleGrantedAuthority(entity.getAuth().toString())).build();
        if (user.getAuth() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

        return tokenFactory.createAccessJwtToken(user);
    }
}
