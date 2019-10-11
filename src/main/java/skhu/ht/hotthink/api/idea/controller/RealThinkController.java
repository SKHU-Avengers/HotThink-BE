package skhu.ht.hotthink.api.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.Idea;
import skhu.ht.hotthink.api.idea.model.Pagination;
import skhu.ht.hotthink.api.idea.service.IdeaServiceImpl;

import java.util.List;

@RestController
@RequestMapping("realThink")
public class RealThinkController {
    @Autowired
    IdeaServiceImpl ideaService;


    /*
    작성자: 홍민석
    작성일: 2019-10-01
    내용: realthink 페이징 GET.
    pagination 객체 수신후 realthink 페이지 List 반환
    */
    @GetMapping(value = "/")
    public List<Idea> realList(@RequestBody Pagination pagination) {
        return ideaService.getIdeaList(pagination);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-01
        내용: realthink 게시물 GET.
        realthink 게시물 id 및 카테고리를 url에 입력하면
        해당하는 realthink 게시물 반환
    */
    @GetMapping(value = "/{realId}/{category}")
    public Idea realRead(@PathVariable("realId") int seq, @PathVariable("category") String category){
        return ideaService.getIdea(seq, category);
    }


}
