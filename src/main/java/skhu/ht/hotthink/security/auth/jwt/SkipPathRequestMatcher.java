package skhu.ht.hotthink.security.auth.jwt;

import io.jsonwebtoken.lang.Assert;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import skhu.ht.hotthink.config.WebSecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher matchers;
    public SkipPathRequestMatcher(List<String> pathsToSkip) {
        Assert.notNull(pathsToSkip);
        List<RequestMatcher> m = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
        matchers = new OrRequestMatcher(m);
    }
    @Override
    public boolean matches(HttpServletRequest request) {
        if (matchers.matches(request)) return false;
        else if(request.getRequestURI().equals("/api/user")&&request.getMethod().equals("POST")) return false;
        return true;
    }

}
