package skhu.ht.hotthink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.security.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/register")
    public String regiester(){
        return "test";
    }
}
