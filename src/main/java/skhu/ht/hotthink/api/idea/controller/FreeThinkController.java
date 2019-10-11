package skhu.ht.hotthink.api.idea.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.service.FreeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("freethink")
public class FreeThinkController {

    @Autowired
    FreeServiceImpl freeService;

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 리스트 READ.
        Pagination 정보를 JSON으로 입력받아
        해당하는 realthink 게시물 리스트 반환
    */
    @GetMapping(value = "/")
    public List<FreeListDTO> freeList(@RequestBody Pagination pagination) {
        return freeService.getFreeList(pagination);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-01
        내용: freethink 게시물 READ.
        freethink 게시물 fr_seq를 입력하면
        해당하는 freethink 게시물 반환
    */
    @GetMapping(value = "/{freeId}")
    public ResponseEntity<?> freeRead(@PathVariable("freeId") Long freeId) {
        FreeOutDTO freeOutDto = freeService.getFree(freeId);
        if(freeOutDto == null){
            return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
        }
        freeOutDto.setReplies(freeService.getReplyList(freeOutDto.getFrSeq()));
        return new ResponseEntity(freeOutDto,HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 CREATE.
        쓰고자 하는 게시물 정보(FreeInDTO)를 JSON으로 입력받아
        새로운 게시물 생성
    */
    @PostMapping(value = "/{nickname}/{category}")
    public ResponseEntity<String> freeCreate(@RequestBody FreeInDTO freeInDto,
                           @PathVariable("nickname") String nickname,
                           @PathVariable("category") String category){
        if(freeService.setFree(freeInDto,nickname,category)==false){
            return new ResponseEntity("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Fail",HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 UPDATE.
        수정하고자 하는 게시물 정보(FreeInDTO)를 JSON으로 입력받아
        원본 게시물 수정.
        TODO:권한 인증 코드 작성
    */
    @PutMapping(value = "/{freeId}/{category}")
    public ResponseEntity<String> freeUpdate(@PathVariable("freeId") Long freeId, @PathVariable("category") String category,
                           @RequestBody FreeInDTO freeInDto){
        if(freeService.putFree(freeId, category, freeInDto)==false){
            return new ResponseEntity("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Fail",HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 DELETE.
        수정하고자 하는 게시물 번호를 입력받아 해당 게시물 삭제.
        삭제 실패시 BAD_REQUEST 반환.
        TODO:권한 인증 코드 작성
    */
    @DeleteMapping(value = "/{freeId}")
    public ResponseEntity<String> freeDelete(@PathVariable("freeId") Long freeId,
                           @RequestBody FreeInDTO freeInDto){

        if(freeService.deleteFree(freeId, freeInDto)!=true){
            return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일:
        내용: 댓글 CREATE.

    */
    @PostMapping(value = "/reply")
    public ResponseEntity<String> replyCreate(@RequestBody ReplyInDTO replyInDto){
        if(freeService.setReply(replyInDto)!=true){
            return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일:
        내용: 댓글 UPDATE.
        TODO:권한 인증 코드 작성
    */
    @PutMapping(value = "/reply/{replyId}")
    public void replyUpdate(){
    }

    /*
        작성자: 홍민석
        작성일:
        내용: 댓글 DELETE.
        TODO:권한 인증 코드 작성
    */
    @DeleteMapping(value = "/{freeId}/reply/{replyId}")
    public void replyDelete(@PathVariable("freeId") String freeId,
                            @PathVariable("replyId") Long replyId){
        freeService.deleteReply(freeId,replyId);

    }
}
