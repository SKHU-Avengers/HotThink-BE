package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

@RequestMapping("api/user")
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    /*
            작성자: 홍민석, 수정자: 김영곤
            작성일: 19-10-07, 수정일: 19-10-23
            내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
    */
    @ResponseBody
    @PostMapping
    public ResponseEntity<String> userCreate(@RequestBody NewUserDTO newUserDto){
        int result = userService.setUser(newUserDto);
        if(result==1) return new ResponseEntity<String>("Email Overlap", HttpStatus.valueOf(409));
        else if(result==2) return new ResponseEntity<String>("NickName Overlap", HttpStatus.valueOf(408));
        return new ResponseEntity<String>("Success", HttpStatus.OK);
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
