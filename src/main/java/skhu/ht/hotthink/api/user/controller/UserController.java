package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

import java.util.List;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @RequestMapping("/{user}")
    public ResponseEntity<String> userCreate(@PathVariable("user") User user){
        userService.saveUser(user);
        return new ResponseEntity<String>("Complete User Create", HttpStatus.OK);
    }

    @RequestMapping("mypage")
    @ResponseBody
    public List<User> users(){
        return userService.findAll();
    }


    /*
    프론트-백앤드 연동후 반드시 삭제할것!

    백앤드 개발 테스트용 로그인기능
     */
    @RequestMapping("user/login/{id}/{password}")
    public void Dev_Test_Login(@PathVariable("id") String id,@PathVariable("password") String password){
        System.out.println("[MyPageController] Dev_Test_Login fun) id:"+id+", password:" + password);

        //MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
        //myAuthenticationProvider.authenticate(id,password);
    }
 }
