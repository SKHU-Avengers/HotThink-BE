package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.MessageState;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    /*
            작성자: 홍민석
            작성일: 19-10-07
            내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
            작성일: 19-10-23
            내용: 이메일, 닉네임이 중복되면 CONFLICT 전달.
    */
    @ResponseBody
    @PostMapping
    public ResponseEntity<?> userCreate(@RequestBody NewUserDTO newUserDto){

        switch(userService.setUser(newUserDto,0)){
            case Created:
                return new ResponseEntity(HttpStatus.CREATED);
            case EmailConflict:
                return new ResponseEntity(MessageState.EmailConflict.name(),HttpStatus.CONFLICT);
            case NickNameConflict:
                return new ResponseEntity(MessageState.NickNameConflict.name(),HttpStatus.CONFLICT);
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

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

    @PostMapping("/register")
    public String regiester(){
        return "test";
    }
 }
