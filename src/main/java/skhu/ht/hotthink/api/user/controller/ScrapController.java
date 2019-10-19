package skhu.ht.hotthink.api.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.ScrapInfoDTO;

@RestController
@RequestMapping("user")
public class ScrapController {


    @GetMapping("/{nickName}/scrap/freethink/{freeId}")
    public void freeScrapListRead(@PathVariable("nickName") String nickName,
                              @PathVariable("freeId") String frSeq){
    }

    @PostMapping("/{nickName}/scrap/freethink/")
    public ResponseEntity<?> freeScrapCreate(@PathVariable("nickName") String nickName,
                                      @RequestBody ScrapInfoDTO scrapInfoDto){


        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/{nickName}/scrap/realthink/{realId}")
    public void realScrapListRead(@PathVariable("nickName") String nickName,
                              @PathVariable("realId") String IdSeq){
    }

    @PostMapping("/{nickName}/scrap/realthink/")
    public ResponseEntity<?> realScrapCreate(@PathVariable("nickName") String nickName,
                                         @RequestBody ScrapInfoDTO scrapInfoDto){


        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/{nickName}/scrap/hotthink/{realId}")
    public void hotScrapListRead(@PathVariable("nickName") String nickName,
                                  @PathVariable("hotId") String IdSeq){
    }

    @PostMapping("/{nickName}/scrap/hotthink/")
    public ResponseEntity<?> hotScrapCreate(@PathVariable("nickName") String nickName,
                                             @RequestBody ScrapInfoDTO scrapInfoDto){


        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @DeleteMapping("/")
    public void scrapDelete(){

    }

}
