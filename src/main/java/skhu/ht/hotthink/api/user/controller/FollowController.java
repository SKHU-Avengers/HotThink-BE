package skhu.ht.hotthink.api.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class FollowController {
    /*
            작성자: 홍민석
            작성일: 19-10-07
            내용: 팔로워 기능
    */
    @PostMapping("/{from}/follow/{to}")
    void followCreat(){

    }

    @GetMapping("/{from}/follow/")
    void followListRead(){

    }

    @DeleteMapping("/{from}/follow/{to}")
    void followDelete(){

    }
}
