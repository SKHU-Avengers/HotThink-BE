package skhu.ht.hotthink.security.auth.jwt;

import org.springframework.stereotype.Component;

@Component
public class BloomFilterTokenVerifier {
    public boolean verify(String jti) {
        return true;
    }
}
