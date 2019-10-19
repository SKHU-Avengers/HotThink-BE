package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

//테스트중
@RequestMapping("user")
//@Controller
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    /*
            작성자: 홍민석
            작성일: 19-10-07
            내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
    */
    @ResponseBody
    @PostMapping("/")
    public ResponseEntity<String> userCreate(@RequestBody NewUserDTO newUserDto){
        if(userService.setUser(newUserDto,0)==false){
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Sucess", HttpStatus.OK);
    }

    /*@ResponseBody
    @RequestMapping("users")
    public List<User> users(){
        return userService.findAll();
    }*/


    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/register")
    public String regiester(){
        return "test";
    }
 }
