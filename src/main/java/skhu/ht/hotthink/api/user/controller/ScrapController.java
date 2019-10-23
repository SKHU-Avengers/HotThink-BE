package skhu.ht.hotthink.api.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.user.model.ScrapInfoDTO;

@RestController
@RequestMapping("api/user")
public class ScrapController {

    @GetMapping("/{nickName}/scrap/freethink/{freeId}")
    public ResponseEntity<?> freeScrapListRead(@PathVariable("nickName") String nickName,
                              @PathVariable("freeId") String frSeq){
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{nickName}/scrap/freethink")
    public ResponseEntity<?> freeScrapCreate(@PathVariable("nickName") String nickName,
                                      @RequestBody ScrapInfoDTO scrapInfoDto){


        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{nickName}/scrap/realthink/{realId}")
    public ResponseEntity<?> realScrapListRead(@PathVariable("nickName") String nickName,
                              @PathVariable("realId") String IdSeq){
        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping("/{nickName}/scrap/realthink/")
    public ResponseEntity<?> realScrapCreate(@PathVariable("nickName") String nickName,
                                         @RequestBody ScrapInfoDTO scrapInfoDto){


        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{nickName}/scrap/hotthink/{realId}")
    public ResponseEntity<?> hotScrapListRead(@PathVariable("nickName") String nickName,
                                  @PathVariable("hotId") String IdSeq){
        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping("/{nickName}/scrap/hotthink/")
    public ResponseEntity<?> hotScrapCreate(@PathVariable("nickName") String nickName,
                                             @RequestBody ScrapInfoDTO scrapInfoDto){


        return new ResponseEntity(HttpStatus.OK);
    }

}
