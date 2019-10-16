package skhu.ht.hotthink.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class APIController {

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

}