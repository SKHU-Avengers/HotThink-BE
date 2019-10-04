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
        작성일: 2019-10-01
        내용: realthink 게시물 GET.
        realthink 게시물 id 및 카테고리를 url에 입력하면
        해당하는 realthink 게시물 반환
    */
    @RequestMapping(value = "freethink", method = RequestMethod.GET)
    public List<FreeListDTO> freeList(@RequestBody Pagination pagination) {
        return ideaService.getFreeList(pagination);
    }

    @RequestMapping(value = "/{freeId}/{category}", method = RequestMethod.GET)
    public FreeOutDTO freeRead(@PathVariable("{freeId}") int freeid, @PathVariable("category") String category) {
        return ideaService.getFree(freeid, category);
    }

    @RequestMapping(value = "/{nickname}/{category}", method = RequestMethod.POST)
    public void freeCreate(@RequestBody FreeInDTO freeInDto,
                           @PathVariable("nickname") String nickname,
                           @PathVariable("category") String category){
        ideaService.setFree(freeInDto,nickname,category);
    }

    @RequestMapping(value = "freethink/{freeId}/{category}", method = RequestMethod.PUT)
    public void freeUpdate(@PathVariable("{freeId}") int freeid, @PathVariable("category") String category,
                           @RequestBody Free free){
        //ideaService.putFree(freeid, category, free,)

    }
}
