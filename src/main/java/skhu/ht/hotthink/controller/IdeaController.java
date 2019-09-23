package skhu.ht.hotthink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.ht.hotthink.domain.Free;
import skhu.ht.hotthink.domain.Idea;
import skhu.ht.hotthink.repository.IdeaRepository;

import java.util.List;

@RestController
public class IdeaController {
    @Autowired
    IdeaRepository ideaRepository;

    @RequestMapping("realthink")
    public List<Idea> realRead(){
        //return ideaRepository.findBySeqAndTBCategoryCategory(, );
    }

    @RequestMapping("freethink")
    public List<Free> freeRead(){
        return
    }
}
