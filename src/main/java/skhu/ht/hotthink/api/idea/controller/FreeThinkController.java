package skhu.ht.hotthink.api.idea.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.CategoryDTO;
import skhu.ht.hotthink.api.idea.model.LikeDTO;
import skhu.ht.hotthink.api.idea.model.LikeOutDTO;
import skhu.ht.hotthink.api.idea.model.PutDTO;
import skhu.ht.hotthink.api.idea.model.boardin.FreeInDTO;
import skhu.ht.hotthink.api.idea.model.boardlist.FreeListDTO;
import skhu.ht.hotthink.api.idea.model.boardout.FreeOutDTO;
import skhu.ht.hotthink.api.idea.model.page.Pagination;
import skhu.ht.hotthink.api.idea.model.reply.ReplyOutDTO;
import skhu.ht.hotthink.api.idea.model.reply.ReplyPutDTO;
import skhu.ht.hotthink.api.idea.model.reply.ReplyInDTO;
import skhu.ht.hotthink.api.idea.service.BoardServiceImpl;
import skhu.ht.hotthink.api.user.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/freethink")
public class FreeThinkController {
    @Autowired
    UserServiceImpl userService;
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
    public ResponseEntity<?> freeList(@RequestParam(value = "sb",defaultValue = "0") Integer searchBy,
                                      @RequestParam("sz") @NonNull Integer size,
                                      @RequestParam("pg") @NonNull Integer page,
                                      @RequestParam(name="ob", defaultValue = "0") Integer orderBy,
                                      @RequestParam(name="category") CategoryDTO category,
                                      @RequestParam(name="st", required = false) String searchText) {

        Pagination pagination = Pagination.builder()
                .category(category.name())
                .page(page)
                .boardType(BoardType.FREE)
                .size(size)
                .orderBy(orderBy)
                .searchBy(searchBy)
                .searchText(searchText)
                .build();
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
        FreeOutDTO freeOutDto = boardService.getOne(freeId, BoardType.FREE, FreeOutDTO.class);
        freeOutDto.setReplies(boardService.getReplyList(freeOutDto.getBdSeq()));
        return new ResponseEntity(freeOutDto,HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: freethink 게시물 CREATE.
        쓰고자 하는 게시물 정보(FreeInDTO)를 JSON으로 입력받아
        새로운 게시물 생성
        작성일: 2019-11-01
        내용: 권한 인증 작성
    */
    @PostMapping(value = "/{category}")
    public ResponseEntity<?> freeCreate(@RequestBody @Valid FreeInDTO freeInDto,
                                        @PathVariable("category") String category) {
        log.debug(category);
        if(boardService.setOne(freeInDto, category, BoardType.FREE)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: freethink 게시물 UPDATE.
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
                                .boardType(BoardType.FREE)
                                .build();

        if(boardService.putOne(putDto)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: freethink 게시물 DELETE.
        수정하고자 하는 게시물 번호를 입력받아 해당 게시물 삭제.
        삭제 실패시 BAD_REQUEST 반환.
        TODO:권한 인증 코드 작성
    */
    @DeleteMapping(value = "/{freeId}")
    public ResponseEntity<?> freeDelete(@PathVariable("freeId") Long freeId){
        if(boardService.deleteOne(freeId, null)) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-11-26
        내용: 게시판 좋아요 리스트 출력 기능입니다.
    */
    @GetMapping("{freeId}/likes")
    public ResponseEntity<?> freeLikeListRead(@PathVariable Long freeId){
        LikeDTO likeDTO = LikeDTO.builder()
                .boardType(BoardType.FREE)
                .seq(freeId)
                .build();
        return new ResponseEntity(boardService.getLikeList(likeDTO), HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 19-11-26
        내용: 게시판 좋아요 리스트 출력 기능입니다.
    */
    @GetMapping("{freeId}/reply/{replyId}/likes")
    public ResponseEntity<?> replyLikeListRead(@PathVariable Long freeId,
                                              @PathVariable Long replyId){
        LikeDTO likeDTO = LikeDTO.builder()
                .boardType(BoardType.REPLY)
                .seq(replyId)
                .build();
        return new ResponseEntity(boardService.getLikeList(likeDTO), HttpStatus.OK);
    }
    /*
        작성자: 홍민석
        작성일: 19-10-26
        내용: freethink 좋아요 기능입니다.
        작성일: 19-11-13
        내용: 프론트 요청으로 이메일 response코드 작성
    */
    @PostMapping(value="/{freeId}/fan")
    public ResponseEntity<?> freeLike(@PathVariable("freeId") Long bdSeq){
        String email = null;
        LikeDTO likeDTO = LikeDTO.builder()
                .seq(bdSeq)
                .boardType(BoardType.FREE)
                .build();
        if((email=boardService.setLike(likeDTO))!=null) return new ResponseEntity(email,HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-11-12
        내용: freethink 좋아요 취소기능입니다.
    */
    @DeleteMapping(value="/{freeId}/fan")
    public ResponseEntity<?> freeUnLike(@PathVariable("freeId") Long bdSeq){
        String email = null;
        LikeDTO likeDTO = LikeDTO.builder()
                .seq(bdSeq)
                .boardType(BoardType.FREE)
                .build();
        if((email=boardService.deleteLike(likeDTO))!=null) return new ResponseEntity(email, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    /*
        작성자: 홍민석
        작성일: 19-10-26
        내용: freethink 댓글 좋아요 기능입니다.
    */
    @PostMapping(value="/{freeId}/reply/{replyId}/fan")
    public ResponseEntity<?> freeReplyLike(@PathVariable("freeId") Long bdSeq,
                                                 @PathVariable("replyId") Long rpSeq){
        String email = null;
        LikeDTO likeDTO = LikeDTO.builder()
                .seq(rpSeq)
                .boardType(BoardType.REPLY)
                .build();
        if((email = boardService.setLike(likeDTO))!=null){
            return new ResponseEntity(email, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    /*
            작성자: 홍민석
            작성일: 19-11-12
            내용: freethink 댓글 좋아요 취소기능입니다.
    */
    @DeleteMapping(value="/{freeId}/reply/{replyId}/fan")
    public ResponseEntity<?> freeUnLike(@PathVariable("freeId") Long bdSeq,
                                             @PathVariable("replyId") Long rpSeq){
        String email=null;
        LikeDTO likeDTO = LikeDTO.builder()
                .seq(rpSeq)
                .boardType(BoardType.REPLY)
                .build();
        if((email = boardService.deleteLike(likeDTO))!=null) return new ResponseEntity(email,HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-25
        내용: 댓글 CREATE.
        작성일: 19-11-01
        내용: 권한인증 코드작성
        작성일: 19-11-21
        내용: 프론트 요청으로 댓글 생성후 생성된 댓글 리스트를 반환하도록 변경.
    */
    @PostMapping(value = "{boardId}/reply")
    public ResponseEntity<?> replyCreate(@PathVariable("boardId") Long boardId,
                                         @RequestBody ReplyInDTO replyInDto){
        replyInDto.setBdSeq(boardId);
        if(boardService.setReply(replyInDto)){
            List<ReplyOutDTO> replies = boardService.getReplyList(boardId);
            return new ResponseEntity(replies, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-25
        내용: 대댓글 CREATE.
        작성일: 19-11-01
        내용: 권한인증 코드작성
        작성일: 19-11-24
        내용: 프론트 요청으로 댓글 생성후 생성된 댓글 리스트를 반환하도록 변경.
    */
    @PostMapping(value = "{freeId}/reply/{replyId}")
    public ResponseEntity<?> subreplyCreate(@PathVariable("freeId") Long boardId,
                                         @PathVariable("replyId")Long replyId,
                                         @RequestBody @Valid ReplyInDTO replyInDto){
        replyInDto.setBdSeq(boardId);
        replyInDto.setSuperRpSeq(replyId);
        if(boardService.setReply(replyInDto)){
            List<ReplyOutDTO> replies = boardService.getReplyList(boardId);
            return new ResponseEntity(replies, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-25
        내용: 댓글 UPDATE.
        작성일: 19-11-01
        내용: 권한인증 코드작성
        작성일: 19-11-25
        내용: 프론트 요청으로 댓글 생성후 생성된 댓글 리스트를 반환하도록 변경.
    */
    @PutMapping(value = "{freeId}/reply/{replyId}")
    public ResponseEntity<?> replyUpdate(@PathVariable("freeId") Long boardId,//rest 및 이후 예외처리 위해 만듦.
                            @PathVariable("replyId") Long replyId,
                            @RequestBody ReplyPutDTO replyPutDto){
        if(boardService.putReply(replyPutDto,replyId)){
            List<ReplyOutDTO> replies = boardService.getReplyList(boardId);
            return new ResponseEntity(replies,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 2019-11-01
        내용: 댓글 DELETE
        서비스에 권한인증 코드작성
    */
    @DeleteMapping(value = "/{freeId}/reply/{replyId}")
    public ResponseEntity<?> replyDelete(@Valid @PathVariable("freeId") Long freeId,
                            @Valid @PathVariable("replyId") Long replyId){
        if(boardService.deleteReply(freeId,replyId)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
