package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.FollowDTO;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class FollowController {
    @Autowired
    UserServiceImpl userService;
    /*
        작성자: 홍민석
        작성일: 19-10-19
        내용: 팔로우 CREATE
    */
    @PostMapping("/{from}/follow/{to}")
    ResponseEntity<String> followCreate(@PathVariable("from") String from,
                                        @PathVariable("to") String to){
        if(!userService.setFollow(from, to)){
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success", HttpStatus.OK);

    }
    /*
        작성자: 홍민석
        작성일: 19-10-19
        내용: 팔로우
    */
    @GetMapping("/{nickname}/follow/")
    ResponseEntity<List<FollowDTO>> followListRead(@PathVariable("nickname") String nickName){
        //List<FollowDTO> followListDTO;
        //TODO : 권한 인증 + HTTP 응답 코드 작성
        return new ResponseEntity<List<FollowDTO>>(userService.getFollowList(nickName), HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-19
        내용: 팔로우
    */
    @GetMapping("/{nickname}/follower/")
    ResponseEntity<List<FollowDTO>> followerListRead(@PathVariable("nickname") String nickName){
        //List<FollowDTO> followListDTO;
        //TODO : 권한 인증 + HTTP 응답 코드 코드 작성
        return new ResponseEntity<List<FollowDTO>>(userService.getFollowerList(nickName), HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-19
        내용: 팔로우 삭제
        //TODO: 권한 인증 코드 작성
    */
    @DeleteMapping("/{from}/follow/{to}")
    ResponseEntity<String> followDelete(@PathVariable("from") String from,
                      @PathVariable("to") String to){

        FollowDTO followInDTO = new FollowDTO();
        if(!userService.deleteFollow(from, to)){
            return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }
}
