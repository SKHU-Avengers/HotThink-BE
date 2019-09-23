package skhu.ht.hotthink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import skhu.ht.hotthink.domain.User;
import skhu.ht.hotthink.service.UserService;

import java.util.List;

@Controller
public class MyPageController {

    @Autowired UserService userService;

    @RequestMapping("/mypage")
    @ResponseBody
    public List<User> users(){
        return userService.findAll();
    }
 }
