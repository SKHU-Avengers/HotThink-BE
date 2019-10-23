package skhu.ht.hotthink.api.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;
import skhu.ht.hotthink.config.WebSecurityConfig;
import skhu.ht.hotthink.security.service.TokenVerificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired TokenVerificationService tokenVerificationService;
    @Autowired UserServiceImpl userService;

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

}