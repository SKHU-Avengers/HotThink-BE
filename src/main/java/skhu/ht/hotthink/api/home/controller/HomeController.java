package skhu.ht.hotthink.api.home.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

}