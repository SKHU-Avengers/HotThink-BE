package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserBase;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

@RequestMapping("api")
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
    @PostMapping("user/create")
    public ResponseEntity<?> userCreate(@RequestBody NewUserDTO newUserDto){

        switch(userService.setUser(newUserDto,0)){
            case Created:
                return new ResponseEntity(HttpStatus.CREATED);
            default:
                return new ResponseEntity(HttpStatus.CONFLICT);

        }
    }

    /*
           작성자: 김영곤
           작성일: 19-10-22
           내용: 회원정보 수정
    */
    @ResponseBody
    @PutMapping("user/update")
    public ResponseEntity<String> userUpdate(@RequestBody UserModificationDTO userDTO){
        return userService.saveUser(userDTO)? new ResponseEntity<String>("Success", HttpStatus.OK) : new ResponseEntity<String>("NickName Overlap", HttpStatus.valueOf(408));
    }

    /*
           작성자: 김영곤
           작성일: 19-10-22
           내용: 마이페이지
    */

    @GetMapping("me")
    public ResponseEntity<?> myPage(){
        System.out.println(userService.findUserInfoByEmail(((UserBase)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail()));
        return null;
    }
 }
