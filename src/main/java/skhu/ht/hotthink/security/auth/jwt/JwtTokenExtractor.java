package skhu.ht.hotthink.security.auth.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("머리가 비면 안됨");
        }
        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("머리가 넘큼");
        }
        System.out.println("추출:"+header.substring(HEADER_PREFIX.length(), header.length()));
        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
