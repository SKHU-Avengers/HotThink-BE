package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.Free;
import skhu.ht.hotthink.api.idea.model.FreeInDTO;
import skhu.ht.hotthink.api.idea.model.FreeListDTO;
import skhu.ht.hotthink.api.idea.model.FreeOutDTO;
import skhu.ht.hotthink.api.idea.model.Pagination;
import skhu.ht.hotthink.api.idea.service.IdeaServiceImpl;

import java.util.List;

@RestController
@RequestMapping("freethink")
public class FreeThinkController {

    @Autowired
    IdeaServiceImpl ideaService;

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 리스트 READ.
        Pagination 정보를 JSON으로 입력받아
        해당하는 realthink 게시물 리스트 반환
    */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<FreeListDTO> freeList(@RequestBody Pagination pagination) {
        return ideaService.getFreeList(pagination);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-01
        내용: realthink 게시물 READ.
        realthink 게시물 seq 및 카테고리를 url에 입력하면
        해당하는 realthink 게시물 반환
    */
    @RequestMapping(value = "/{freeId}/{category}", method = RequestMethod.GET)
    public FreeOutDTO freeRead(@PathVariable("freeId") Long freeId, @PathVariable("category") String category) {
        return ideaService.getFree(freeId, category);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 CREATE.
        쓰고자 하는 게시물 정보(FreeInDTO)를 JSON으로 입력받아
        새로운 게시물 생성
    */
    @RequestMapping(value = "/{nickname}/{category}", method = RequestMethod.POST)
    public void freeCreate(@RequestBody FreeInDTO freeInDto,
                           @PathVariable("nickname") String nickname,
                           @PathVariable("category") String category){
        ideaService.setFree(freeInDto,nickname,category);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 UPDATE.
        수정하고자 하는 게시물 정보(FreeInDTO)를 JSON으로 입력받아
        원본 게시물 수정.
        TODO:권한 인증 코드 작성
    */
    @RequestMapping(value = "/{freeId}/{category}", method = RequestMethod.PUT)
    public void freeUpdate(@PathVariable("freeId") Long freeId, @PathVariable("category") String category,
                           @RequestBody FreeInDTO freeInDto){
        ideaService.putFree(freeId, category, freeInDto);

    }
}
