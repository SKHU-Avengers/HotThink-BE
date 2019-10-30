package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.PutDTO;
import skhu.ht.hotthink.api.idea.model.page.IdeaPagination;
import skhu.ht.hotthink.api.idea.model.boardin.RealInDTO;
import skhu.ht.hotthink.api.idea.model.boardlist.RealListDTO;
import skhu.ht.hotthink.api.idea.model.boardout.RealOutDTO;
import skhu.ht.hotthink.api.idea.service.BoardServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/realthink")
public class RealThinkController {
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
    public ResponseEntity<?> realList(@RequestBody IdeaPagination pagination) {
        List<RealListDTO> real = boardService.getBoardList(pagination,RealListDTO.class);
        return new ResponseEntity(real, HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-01
        내용: realthink 게시물 READ.
        realthink 게시물 id_seq를 입력하면
        해당하는 realthink 게시물 반환
    */
    @GetMapping(value = "/{realId}")
    public ResponseEntity<?> realRead(@PathVariable("realId") Long realId) {
        RealOutDTO realOutDto = boardService.getOne(realId,RealOutDTO.class);
        return new ResponseEntity(realOutDto,HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 CREATE.
        쓰고자 하는 게시물 정보(RealInDTO)를 JSON으로 입력받아
        새로운 게시물 생성
    */
    @PostMapping(value = "/{nickname}/{category}")
    public ResponseEntity<?> realCreate(@RequestBody RealInDTO realInDto,
                                             @PathVariable("nickname") String nickname,
                                             @PathVariable("category") String category){
        if(boardService.setOne(realInDto, nickname, category, BoardType.REAL)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 UPDATE.
        수정하고자 하는 게시물 정보(RealInDTO)를 JSON으로 입력받아
        원본 게시물 수정.
        TODO:권한 인증 코드 작성
    */
    @PutMapping(value = "/{realId}/{category}")
    public ResponseEntity<?> realUpdate(@PathVariable("realId") Long realId, @PathVariable("category") String category,
                                             @RequestBody RealInDTO realInDto){
        PutDTO putDto = PutDTO.builder()
                .bdSeq(realId)
                .title(realInDto.getTitle())
                .contents(realInDto.getContents())
                .image(realInDto.getImage())
                .real(realInDto.getReal())
                .boardType(BoardType.REAL)
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
    @DeleteMapping(value = "/{realId}")
    public ResponseEntity<?> realDelete(@PathVariable("realId") Long realId,
                                             @RequestBody RealInDTO realInDto){
        if(boardService.deleteOne(realId, realInDto)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
