package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.exception.UserConflictException;
import skhu.ht.hotthink.api.user.model.*;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserInfoDTO;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.api.user.service.EmailService;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;
import skhu.ht.hotthink.error.ErrorCode;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    EmailService emailService;
    @Autowired
    JavaMailSender javaMailSender;

    /*
            작성자: 홍민석, 김영곤
            작성일: 19-10-07
            내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
            작성일: 19-10-23
            내용: 이메일, 닉네임이 중복되면 CONFLICT 전달.
            작성일: 19-10-25
            내용: 메일인증 작성
            작성일: 19-11-13
            내용: PreferenceDTO 작성
    */
    @ResponseBody
    @PostMapping("user")
    public ResponseEntity<?> userCreate(@RequestBody NewUserDTO newUserDto){
        try{
            userService.setUser(newUserDto,0);
        }catch (UserConflictException exception){
            if(exception.getErrorCode().equals(ErrorCode.EMAIL_CONFLICT)) return new ResponseEntity(HttpStatus.CONFLICT);
            if(exception.getErrorCode().equals(ErrorCode.NICKNAME_CONFLICT)) return new ResponseEntity(HttpStatus.valueOf(408));
        }
        return new ResponseEntity(HttpStatus.CREATED);
        /*
        emailService.setEmailSender(javaMailSender);
        if(emailService.sendEmail(newUserDto)) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
         */
    }
/*
    @GetMapping("/user/{nickName}/key/{emailKey}")
    public ResponseEntity<?> confirmEmail(@PathVariable("nickName") String nickName,
                                          @PathVariable("emailKey") Long emailKey){
        switch(emailService.confirmEmail(nickName,emailKey)){
            case Success:

        }
    }
*/


    /*
           작성자: 김영곤
           작성일: 19-10-22
           내용: 회원정보 수정
    */
    @ResponseBody
    @PutMapping("user")
    public ResponseEntity<String> userUpdate(@RequestBody UserModificationDTO userDTO){
        return userService.saveUser(userDTO)? new ResponseEntity<String>("Success", HttpStatus.OK) : new ResponseEntity<String>("NickName Overlap", HttpStatus.valueOf(408));
    }

    /*
           작성자: 김영곤
           작성일: 19-10-22
           내용: 마이페이지
           작성일: 19-10-27
           내용: 문혁이의 부탁으로 url 로그인 체크로 변경
    */
    @GetMapping("login/check")
    public @ResponseBody UserInfoDTO myPage(){
        return userService.findUserInfo();
    }

    //결제 전 임시로 포인트 충전
    @PostMapping("user/point")
    public void pointCharging(@RequestBody HashMap<String, Integer> point){
        userService.pointCharge(point.get("point"));
    }

    @GetMapping("user/follow")
    public @ResponseBody HashMap<String, List<UserBase>> userFollowList(){
        return userService.getUserFollowList();
    }

    @GetMapping("user")
    public @ResponseBody TargetUserDTO getTargetUser(@RequestParam("nickName") String nickName){
        return userService.getUserByNickName(nickName);
    }
}
