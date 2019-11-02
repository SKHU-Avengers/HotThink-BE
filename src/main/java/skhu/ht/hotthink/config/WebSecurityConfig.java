package skhu.ht.hotthink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import skhu.ht.hotthink.security.auth.jwt.SkipPathRequestMatcher;
import skhu.ht.hotthink.security.auth.login.LoginAuthenticationProvier;
import skhu.ht.hotthink.security.auth.login.LoginProcessingFilter;
import skhu.ht.hotthink.security.auth.jwt.JwtAuthenticationProvider;
import skhu.ht.hotthink.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import skhu.ht.hotthink.security.auth.jwt.JwtTokenExtractor;
import skhu.ht.hotthink.security.auth.AuthenticationHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String API_ROOT_URL = "/api";
    public static final String LOGIN_PROCESSING_URL = "/api/login/processing";
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String REFRESH_TOKEN_URL = "/auth/token";

    @Autowired private AuthenticationHandler authenticationHandler;
    @Autowired private LoginAuthenticationProvier loginAuthenticationProvider;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired private RestAuthenticationEntryPoint entryPoint;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private JwtTokenExtractor tokenExtractor;


    protected LoginProcessingFilter buildLoginProcessingFilter() {
        LoginProcessingFilter filter = new LoginProcessingFilter(LOGIN_PROCESSING_URL, authenticationHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() {
        List<String> pathsToSkip = Arrays.asList(API_ROOT_URL, REFRESH_TOKEN_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(authenticationHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                //csrf 공격 방어기능 비활성화
                .csrf().disable()

                //인증되지 않은 request -> 401 status
                .exceptionHandling()
                .authenticationEntryPoint(this.entryPoint)

                //stateless
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //root,login,tokenRefresh 빼고 나머지는 권한 요청
                .and()
                    .authorizeRequests()
                    .antMatchers(API_ROOT_URL, REFRESH_TOKEN_URL).permitAll()
                    .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .anyRequest().authenticated()

                .and()
                    .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

//                .failureUrl(LOGIN_ERROR_URL);
//                .usernameParameter("email")
//                .passwordParameter("password");




    }


}
