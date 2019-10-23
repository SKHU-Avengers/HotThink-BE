package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.MessageState;
import skhu.ht.hotthink.api.domain.BoardType;
import skhu.ht.hotthink.api.idea.model.*;
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
        if(real==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
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
        if(realOutDto == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
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
        switch(boardService.setOne(realInDto, nickname, category, BoardType.REAL.name())){
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
        수정하고자 하는 게시물 정보(RealInDTO)를 JSON으로 입력받아
        원본 게시물 수정.
        TODO:권한 인증 코드 작성
    */
    @PutMapping(value = "/{realId}/{category}")
    public ResponseEntity<?> realUpdate(@PathVariable("realId") Long realId, @PathVariable("category") String category,
                                             @RequestBody RealInDTO realInDto){
        switch(boardService.putOne(realId, category, realInDto)) {
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
    @DeleteMapping(value = "/{realId}")
    public ResponseEntity<?> realDelete(@PathVariable("realId") Long realId,
                                             @RequestBody RealInDTO realInDto){
        switch(boardService.deleteOne(realId, realInDto)){
            case Success:
                return new ResponseEntity(HttpStatus.OK);
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }



}
