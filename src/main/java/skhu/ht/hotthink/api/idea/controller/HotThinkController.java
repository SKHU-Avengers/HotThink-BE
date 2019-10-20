package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.service.IdeaServiceImpl;

import java.util.List;

@RestController
@RequestMapping("hotthink")
public class HotThinkController {
    @Autowired
    IdeaServiceImpl<HotInDTO, HotOutDTO, HotListDTO> ideaService;
    /*
            작성자: 홍민석
            작성일: 2019-10-07
            내용: hotthink 게시물 리스트 READ.
            Pagination 정보를 JSON으로 입력받아
            해당하는 hotthink 게시물 리스트 반환
    */
    @GetMapping(value = "/")
    public List<HotListDTO> hotList(@RequestBody IdeaPagination pagination) {
        return ideaService.getIdeaList(pagination);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-01
        내용: hotthink 게시물 READ.
        hotthink 게시물 id_seq를 입력하면
        해당하는 hotthink 게시물 반환
    */
    @GetMapping(value = "/{hotId}")
    public ResponseEntity<?> hotRead(@PathVariable("hotId") Long hotId) {
        HotOutDTO hotOutDto = ideaService.getIdea(hotId);
        if(hotOutDto == null){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(hotOutDto,HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: hotthink 게시물 CREATE.
        쓰고자 하는 게시물 정보(HotInDTO)를 JSON으로 입력받아
        새로운 게시물 생성

    @PostMapping(value = "/{nickname}/{category}")
    public ResponseEntity<String> hotCreate(@RequestBody HotInDTO hotInDto,
                                             @PathVariable("nickname") String nickname,
                                             @PathVariable("category") String category){
        if(!ideaService.setIdea(hotInDto, nickname, category)){
            return new ResponseEntity("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Success",HttpStatus.OK);
    }
    */
    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: hotthink 게시물 UPDATE.
        수정하고자 하는 게시물 정보(HotInDTO)를 JSON으로 입력받아
        원본 게시물 수정.
        TODO:권한 인증 코드 작성
    */
    @PutMapping(value = "/{hotId}/{category}")
    public ResponseEntity<String> hotUpdate(@PathVariable("hotId") Long hotId, @PathVariable("category") String category,
                                             @RequestBody HotInDTO hotInDto){
        if(!ideaService.putIdea(hotId, category, hotInDto)){
            return new ResponseEntity("Fail",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Success",HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: hotthink 게시물 DELETE.
        수정하고자 하는 게시물 번호를 입력받아 해당 게시물 삭제.
        삭제 실패시 BAD_REQUEST 반환.
        TODO:권한 인증 코드 작성
    */
    @DeleteMapping(value = "/{hotId}")
    public ResponseEntity<String> hotDelete(@PathVariable("hotId") Long hotId,
                                             @RequestBody HotInDTO hotInDto){

        if(!ideaService.deleteIdea(hotId, hotInDto)){
            return new ResponseEntity<String>("Fail",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }
}
