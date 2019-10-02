package skhu.ht.hotthink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.domain.Free;
import skhu.ht.hotthink.domain.Idea;
import skhu.ht.hotthink.model.Pagination;
import skhu.ht.hotthink.service.IdeaService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("Idea")
public class IdeaController {
    @Autowired IdeaService ideaService;


    /*
    작성자: 홍민석
    작성일: 2019-10-01
    내용: realthink 페이징.
    pagination 객체 수신후 realthink 페이지 List 반환
     */
    @Secured({"ROLE_SUBSCR","ROLE_MANAGE"})
    @RequestMapping(value = "realthink", method = RequestMethod.GET)
    public List<Idea> realList(@RequestBody Pagination pagination) {
        return ideaService.getRealList(pagination);
    }

    @Secured({"ROLE_SUBSCR","ROLE_MANAGE"})
    @RequestMapping(value = "realthink/{realId}/{category}", method = RequestMethod.GET)
    public Idea realRead(@PathVariable("realId") int seq, @PathVariable("category") String category){
        return ideaService.getReal(seq, category);
    }


    @Secured({"ROLE_MEMBER","ROLE_SUBSCR","ROLE_MANAGE"})
    @RequestMapping(value = "freethink", method = RequestMethod.GET)
    public List<Free> freeList(@RequestBody Pagination pagination) {
        return ideaService.getFreeList(pagination);
    }

    @Secured({"ROLE_MEMBER","ROLE_SUBSCR","ROLE_MANAGE"})
    @RequestMapping(value = "freethink/{freeId}/{category}", method = RequestMethod.GET)
    public Free freeRead(@PathVariable("{freeId}") int freeid, @PathVariable("category") String category) {
        return ideaService.getFree(freeid, category);
    }

    @Secured({"ROLE_MEMBER","ROLE_SUBSCR","ROLE_MANAGE"})
    @RequestMapping(value = "freethink", method = RequestMethod.POST)
    public void freeCreate(@RequestBody Free free){
        ideaService.setFree(free);
    }

    @Secured({"ROLE_MEMBER","ROLE_SUBSCR","ROLE_MANAGE"})
    @RequestMapping(value = "freethink/{freeId}/{category}", method = RequestMethod.PUT)
    public void freeUpdate(@PathVariable("{freeId}") int freeid, @PathVariable("category") String category,
                           @RequestBody Free free, HttpSession session){
        if(ideaService.putFree(freeid, category, free, (String) session.getAttribute(""))){

        }
    }
}
