package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;
import skhu.ht.hotthink.security.service.TokenVerificationService;

@RequestMapping("api/user")
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
    @PostMapping
    public ResponseEntity<?> userCreate(@RequestBody NewUserDTO newUserDto){

        switch(userService.setUser(newUserDto,0)){
            case Created:
                return new ResponseEntity(HttpStatus.CREATED);
            default:
                return new ResponseEntity(HttpStatus.CONFLICT);

        }
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

    /*
           작성자: 김영곤
           작성일: 19-10-22
           내용: 회원정보 수정
    */
    @PutMapping
    @ResponseBody
    public ResponseEntity<String> userUpdate(@RequestBody UserModificationDTO userDTO){
        return userService.saveUser(userDTO)? new ResponseEntity<String>("Success", HttpStatus.OK) : new ResponseEntity<String>("NickName Overlap", HttpStatus.valueOf(408));
    }
 }
