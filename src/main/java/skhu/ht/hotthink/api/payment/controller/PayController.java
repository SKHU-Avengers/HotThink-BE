package skhu.ht.hotthink.api.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.payment.model.ReputationType;
import skhu.ht.hotthink.api.payment.model.dto.*;
import skhu.ht.hotthink.api.payment.service.PayServiceImpl;

@RestController
@RequestMapping("api/pay")
public class PayController {
    @Autowired
    PayServiceImpl payService;


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeCreate(@RequestBody SubscribeInDTO subscribeDTO){
        final int unitPrice = 3000;

        PayInfoDTO payInfoDTO = PayInfoDTO.SubscribeBuilder()
                .payMethod(subscribeDTO.getPayMethod())
                .period(subscribeDTO.getPeriod())
                .price(unitPrice)
                .build();
        if(payService.setOne(payInfoDTO)) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/freepass")
    public ResponseEntity<?> freepassCreate(@RequestBody FreepassInDTO freepassDTO){
        final int unitPrice = 5000;

        PayInfoDTO payInfoDTO = PayInfoDTO.FreePassBuilder()
                .payMethod(freepassDTO.getPayMethod())
                .price(unitPrice)
                .build();
        if(payService.setOne(payInfoDTO)) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/realthink")
    public ResponseEntity<?> realthinkCreate(@RequestBody RealThinkInDTO realThinkDTO){
        PayInfoDTO payInfoDTO = PayInfoDTO.RealThinkBuilder()
                .payMethod(realThinkDTO.getPayMethod())
                .bdSeq(realThinkDTO.getBdSeq())
                .price(1000)//TODO:가격설정
                .comments(realThinkDTO.getBuyerComments())
                .score(realThinkDTO.getBuyerScore())
                .build();
        if(payService.setOne(payInfoDTO)) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reputation/{type}")
    public ResponseEntity<?> reputationCreate(@PathVariable("type")ReputationType reputationType,
                                              @RequestBody ReputationDTO reputationDTO){
        reputationDTO.setReputationType(reputationType);
        if(payService.setReputation(reputationDTO)) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
