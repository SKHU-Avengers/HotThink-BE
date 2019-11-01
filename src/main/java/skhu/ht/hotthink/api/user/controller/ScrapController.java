package skhu.ht.hotthink.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

@RestController
@RequestMapping("api/user")
public class ScrapController {

    @Autowired
    UserServiceImpl userService;

    /*
        작성일: 2019-10-24
        작성자: 홍민석
        내용: 스크랩 생성,
        삭제 성공시 CREATED 반환
        2019-11-01
        내용: ScrapInDTO제거
     */
    @PostMapping("/{nickName}/scrap/{boardId}")
    public ResponseEntity<?> ScrapCreate(@PathVariable("nickName") String nickName,
                                             @PathVariable("boardId") Long boardId){
        if(userService.setScrap(nickName, boardId))return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    /*
        작성일: 2019-10-24
        작성자: 홍민석
        내용: 존재하는 스크랩 삭제,
        삭제 성공시 OK 반환
     */
    @DeleteMapping("/{nickName}/scrap/{boardId}")
    public ResponseEntity<?> ScrapDelete(@PathVariable("nickName") String nickName,
                                             @PathVariable("boardId") Long boardId){
        if(userService.deleteScrap(nickName, boardId)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
/*
    @GetMapping("/{nickName}/scrap/freethink/{freeId}")
    public ResponseEntity<?> freeScrapListRead(@PathVariable("nickName") String nickName,
                              @PathVariable("freeId") Long seq){
        switch(userService.setScrap(nickName,seq)){
            case Created:
                return new ResponseEntity(HttpStatus.OK);
            default:
                return new ResponseEntity(HttpStatus.CONFLICT);
        }


    }

    @GetMapping("/{nickName}/scrap/realthink/{realId}")
    public ResponseEntity<?> realScrapListRead(@PathVariable("nickName") String nickName,
                              @PathVariable("realId") Long seq){
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/{nickName}/scrap/hotthink/{realId}")
    public ResponseEntity<?> hotScrapListRead(@PathVariable("nickName") String nickName,
                                  @PathVariable("hotId") Long seq){
        return new ResponseEntity(HttpStatus.OK);
    }
*/

}
