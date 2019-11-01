package skhu.ht.hotthink.security.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skhu.ht.hotthink.config.JwtSetting;

@Component
public class BloomFilterTokenVerifier {
    @Autowired
    JwtSetting jwtSetting;
    public boolean verify(String jti) {
        return jwtSetting.getJti().equals(jti);
    }
}
