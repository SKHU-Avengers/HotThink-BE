package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.BoardType;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.service.BoardServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/freethink")
public class FreeThinkController {

    @Autowired
    BoardServiceImpl boardService;

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 리스트 READ.
        Pagination 정보를 JSON으로 입력받아
        해당하는 realthink 게시물 리스트 반환
    */
    @GetMapping
    public ResponseEntity<?> freeList(@RequestBody Pagination pagination) {
        List<FreeListDTO> free = boardService.getBoardList(pagination,FreeListDTO.class);
        if(free == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(free,HttpStatus.OK);
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
        FreeOutDTO freeOutDto = boardService.getOne(freeId,FreeOutDTO.class);
        if(freeOutDto == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        freeOutDto.setReplies(boardService.getReplyList(freeOutDto.getFrSeq()));
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
    public ResponseEntity<?> freeCreate(@RequestBody FreeInDTO freeInDto,
                           @PathVariable("nickname") String nickname,
                           @PathVariable("category") String category) {
        switch (boardService.setOne(freeInDto, nickname, category, BoardType.FREE.name())) {
            case Created:
                return new ResponseEntity(HttpStatus.OK);
            case NotExist:
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> freeUpdate(@PathVariable("freeId") Long freeId, @PathVariable("category") String category,
                           @RequestBody FreeInDTO freeInDto){
        switch(boardService.putOne(freeId, category, freeInDto)){
            case Success:
                return new ResponseEntity(HttpStatus.OK);
            default:
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<?> freeDelete(@PathVariable("freeId") Long freeId,
                           @RequestBody FreeInDTO freeInDto){

        switch(boardService.deleteOne(freeId, freeInDto)){
            case Success:
                return new ResponseEntity(HttpStatus.OK);
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    /*
    @PostMapping(value="/{freeId}/fan/{nickname}")
    public ResponseEntity<String> freeLikeCreate(@PathVariable("freeId") Long frSeq,
                                                 @PathVariable("nickname") String nickName){
        if(boardService.setFreeLike(frSeq,nickName)){
            return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }
    */
    /*
        작성자: 홍민석
        작성일:
        내용: 댓글 CREATE.

    */
    @PostMapping(value = "/reply")
    public ResponseEntity<?> replyCreate(@RequestBody ReplyInDTO replyInDto){
        switch(boardService.setReply(replyInDto)){
            case Created:
                return new ResponseEntity(HttpStatus.CREATED);
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
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
        boardService.deleteReply(freeId,replyId);

    }
}
