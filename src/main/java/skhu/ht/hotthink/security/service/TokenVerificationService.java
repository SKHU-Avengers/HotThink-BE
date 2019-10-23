package skhu.ht.hotthink.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.config.JwtSetting;
import skhu.ht.hotthink.security.auth.jwt.BloomFilterTokenVerifier;
import skhu.ht.hotthink.security.auth.jwt.JwtTokenExtractor;
import skhu.ht.hotthink.security.model.token.RawAccessJwtToken;

@Service
public class TokenVerificationService {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtSetting setting;
    @Autowired private BloomFilterTokenVerifier tokenVerifier;
    @Autowired private JwtTokenExtractor tokenExtractor;

    /*
        작성자: 김영곤
        작성일: 19-10-22
        내용: 토큰 검증
    */
    public boolean memberVerifier(String header) {
        String payload = tokenExtractor.extract(header);
        RawAccessJwtToken rawToken = new RawAccessJwtToken(payload);

        Jws<Claims> claims = rawToken.parseClaims(setting.getTokenSigningKey());
        String auth = claims.getBody().get("auth", String.class);
        if(auth == null || !auth.equals("ROLE_MEMBER")) {
            System.out.println("test1");
            return false;//유저 권한 검증
        }
        else if(!tokenVerifier.verify(claims.getBody().getId())) {
            System.out.println("test2");
            return false;//토큰 기간 검증
        }
        else if(userRepository.findUserByEmail(claims.getBody().getSubject())==null) {
            System.out.println("test3");
            return false;//유저 존재 유무 검증
        }
        return true;
    }
}
