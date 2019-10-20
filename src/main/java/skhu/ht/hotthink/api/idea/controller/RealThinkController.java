package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.service.IdeaServiceImpl;

import java.util.List;

@RestController
@RequestMapping("realthink")
public class RealThinkController {
    @Autowired
    IdeaServiceImpl<RealInDTO,RealOutDTO,RealListDTO> ideaService;
    /*
            작성자: 홍민석
            작성일: 2019-10-07
            내용: realthink 게시물 리스트 READ.
            Pagination 정보를 JSON으로 입력받아
            해당하는 realthink 게시물 리스트 반환
    */
    @GetMapping(value = "/")
    public List<RealListDTO> realList(@RequestBody IdeaPagination pagination) {
        return ideaService.getIdeaList(pagination);
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
        RealOutDTO realOutDto = ideaService.getIdea(realId);
        if(realOutDto == null){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> realCreate(@RequestBody RealInDTO realInDto,
                                             @PathVariable("nickname") String nickname,
                                             @PathVariable("category") String category){
        if(!ideaService.setIdea(realInDto, nickname, category)){
            return new ResponseEntity("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Success",HttpStatus.OK);
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
    public ResponseEntity<String> realUpdate(@PathVariable("realId") Long realId, @PathVariable("category") String category,
                                             @RequestBody RealInDTO realInDto){
        if(!ideaService.putIdea(realId, category, realInDto)){
            return new ResponseEntity("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Success",HttpStatus.OK);
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
    public ResponseEntity<String> realDelete(@PathVariable("realId") Long realId,
                                             @RequestBody RealInDTO realInDto){

        if(!ideaService.deleteIdea(realId, realInDto)){
            return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }



}
