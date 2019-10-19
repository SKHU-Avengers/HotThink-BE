package skhu.ht.hotthink.security.auth.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import skhu.ht.hotthink.security.auth.AuthenticationHandler;
import skhu.ht.hotthink.security.model.dto.UserLoginRequestModel;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationHandler handler;
    private final ObjectMapper objectMapper;

    public LoginProcessingFilter(String defaultProcessUrl, AuthenticationHandler handler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.handler = handler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        logger.info("로그인 필터 진입");
        if(request.getContentType().matches(MediaType.APPLICATION_JSON_VALUE)){
            UserLoginRequestModel user = objectMapper.readValue(request.getReader(), UserLoginRequestModel.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPw()));
        }else throw new AccessDeniedException(request.getContentType()+" 쓰지마셈");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        handler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        handler.onAuthenticationFailure(request, response, failed);
    }
}
