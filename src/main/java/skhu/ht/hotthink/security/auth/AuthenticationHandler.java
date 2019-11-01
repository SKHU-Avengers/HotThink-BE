package skhu.ht.hotthink.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import skhu.ht.hotthink.security.exceptions.JwtExpiredTokenException;
import skhu.ht.hotthink.security.exceptions.PasswordErrorException;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;
import skhu.ht.hotthink.config.ResponseMessage;
import skhu.ht.hotthink.security.model.token.JwtToken;
import skhu.ht.hotthink.security.model.token.JwtTokenFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;

    @Autowired
    public AuthenticationHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory){
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("success");

        UserAuthenticationModel user =(UserAuthenticationModel)authentication.getPrincipal();
        JwtToken accessToken = tokenFactory.createAccessJwtToken(user);
        JwtToken refreshToken = tokenFactory.createRefreshToken(user);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", accessToken.getToken());
        tokens.put("refreshToken", refreshToken.getToken());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mapper.writeValue(response.getWriter(), tokens);

        clearAuthenticationAttributes(request);
    }

    private final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info("fail");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if(e instanceof PasswordErrorException)
            mapper.writeValue(response.getWriter(), ResponseMessage.of(e.getMessage(), "Login Failed"));
        else if(e instanceof JwtExpiredTokenException)
            mapper.writeValue(response.getWriter(), ResponseMessage.of(e.getMessage(), "Jwt Authentication Failed"));
        else if (e instanceof UsernameNotFoundException)
            mapper.writeValue(response.getWriter(), ResponseMessage.of(e.getMessage(), "Authentication Failed"));
        else if (e instanceof BadCredentialsException)
            mapper.writeValue(response.getWriter(), ResponseMessage.of(e.getMessage(), "Bad Credential"));
//        else if (e instanceof JwtExpiredTokenException)
//            mapper.writeValue(response.getWriter(), ErrorMessage.of("Token has expired", ErrorMessage.ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
////        } else if (e instanceof AuthMethodNotSupportedException) {
////            mapper.writeValue(response.getWriter(), LoginError.of(e.getMessage(), LoginError.ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));

        mapper.writeValue(response.getWriter(), ResponseMessage.of("Authentication_Fail",  HttpStatus.UNAUTHORIZED.toString()));
//        request.getRequestDispatcher("/api/login/error").forward(request,response);
    }
}
