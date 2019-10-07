package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

import java.util.List;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    /*
            작성자: 홍민석
            작성일: 19-10-07
            내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
    */
    @ResponseBody
    @RequestMapping("/")
    public ResponseEntity<String> userCreate(@RequestBody NewUserDTO newUserDto){
        userService.setUser(newUserDto,0);
        //TODO: 회원가입 실패시 반환 코드 작성
        return new ResponseEntity<String>("Complete User Create", HttpStatus.OK);
    }

    @RequestMapping("mypage")
    @ResponseBody
    public List<User> users(){
        return userService.findAll();
    }
 }
