package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.LikeDTO;
import skhu.ht.hotthink.api.idea.model.PutDTO;
import skhu.ht.hotthink.api.idea.model.boardin.FreeInDTO;
import skhu.ht.hotthink.api.idea.model.boardlist.FreeListDTO;
import skhu.ht.hotthink.api.idea.model.boardout.FreeOutDTO;
import skhu.ht.hotthink.api.idea.model.page.Pagination;
import skhu.ht.hotthink.api.idea.model.reply.ReplyInDTO;
import skhu.ht.hotthink.api.idea.service.BoardServiceImpl;

import javax.validation.Valid;
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
    public ResponseEntity<?> freeList(@RequestBody @Valid Pagination pagination) {
        pagination.setBoardType(BoardType.FREE);
        List<FreeListDTO> free = boardService.getBoardList(pagination,FreeListDTO.class);
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
        freeOutDto.setReplies(boardService.getReplyList(freeOutDto.getBdSeq()));
        return new ResponseEntity(freeOutDto,HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: freethink 게시물 CREATE.
        쓰고자 하는 게시물 정보(FreeInDTO)를 JSON으로 입력받아
        새로운 게시물 생성
    */
    @PostMapping(value = "/{nickname}/{category}")
    public ResponseEntity<?> freeCreate(@RequestBody @Valid FreeInDTO freeInDto,
                           @PathVariable("nickname") String nickname,
                           @PathVariable("category") String category) {
        if(boardService.setOne(freeInDto, nickname, category, BoardType.FREE)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
                           @RequestBody @Valid FreeInDTO freeInDto){
        PutDTO putDto = PutDTO.builder()
                                .bdSeq(freeId)
                                .title(freeInDto.getTitle())
                                .contents(freeInDto.getContents())
                                .image(freeInDto.getImage())
                                .boardType(BoardType.FREE)
                                .build();

        if(boardService.putOne(putDto)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
                           @RequestBody @Valid FreeInDTO freeInDto){

        if(boardService.deleteOne(freeId, freeInDto)) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-26
        내용: freethink 좋아요 기능입니다.
        TODO: nickName여부 프런트와 상의 필요
    */
    @PostMapping(value="/{freeId}/fan/{nickname}")
    public ResponseEntity<String> freeLike(@PathVariable("freeId") Long bdSeq,
                                                 @PathVariable("nickname") String nickName){
        LikeDTO likeDTO = LikeDTO.builder()
                .boardId(bdSeq)
                .nickName(nickName)
                .boardType(BoardType.FREE)
                .build();
        if(boardService.setLike(likeDTO)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    /*
        작성자: 홍민석
        작성일: 19-10-26
        내용: freethink 댓글 좋아요 기능입니다.
        TODO: nickName여부 프런트와 상의 필요
    */
    @PostMapping(value="/{freeId}/reply/{replyId}/fan/{nickname}")
    public ResponseEntity<String> freeReplyLike(@PathVariable("freeId") Long bdSeq,
                                                 @PathVariable("replyId") Long rpSeq,
                                                 @PathVariable("nickname") String nickName){
        LikeDTO likeDTO = LikeDTO.builder()
                .boardId(bdSeq)
                .replyId(rpSeq)
                .nickName(nickName)
                .boardType(BoardType.REPLY)
                .build();
        if(boardService.setLike(likeDTO)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-25
        내용: 댓글 CREATE.
    */
    @PostMapping(value = "/reply")
    public ResponseEntity<?> replyCreate(@RequestBody ReplyInDTO replyInDto){
        if(boardService.setReply(replyInDto)){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-25
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
    public void replyDelete(@PathVariable("freeId") Long freeId,
                            @PathVariable("replyId") Long replyId){
        boardService.deleteReply(freeId,replyId);

    }
}
