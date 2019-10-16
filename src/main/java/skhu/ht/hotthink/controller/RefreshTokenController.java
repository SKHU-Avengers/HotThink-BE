package skhu.ht.hotthink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import skhu.ht.hotthink.security.config.WebSecurityConfig;
import skhu.ht.hotthink.security.model.token.JwtToken;
import skhu.ht.hotthink.security.service.TokenRefreshService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RefreshTokenController {
    @Autowired
    TokenRefreshService tokenRefreshService;

    @GetMapping("/api/auth/token")
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
        return tokenRefreshService.tokenRefresh(header);
    }
}
