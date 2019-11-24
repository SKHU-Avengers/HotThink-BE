package skhu.ht.hotthink.api.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.ht.hotthink.api.payment.model.dto.FreepassInDTO;
import skhu.ht.hotthink.api.payment.model.dto.PayInfoDTO;
import skhu.ht.hotthink.api.payment.model.dto.RealThinkInDTO;
import skhu.ht.hotthink.api.payment.model.dto.SubscribeInDTO;
import skhu.ht.hotthink.api.payment.service.PayServiceImpl;

@RestController
@RequestMapping("api/pay")
public class PayController {
    @Autowired
    PayServiceImpl payService;


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeCreate(SubscribeInDTO subscribeDTO){
        final int unitPrice = 3000;

        PayInfoDTO payInfoDTO = PayInfoDTO.SubscribeBuilder()
                .payMethod(subscribeDTO.getPayMethod())
                .period(subscribeDTO.getPeriod())
                .price(unitPrice)
                .build();
        payService.setOne(payInfoDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/freepass")
    public ResponseEntity<?> freepassCreate(FreepassInDTO freepassDTO){
        final int unitPrice = 5000;

        PayInfoDTO payInfoDTO = PayInfoDTO.FreePassBuilder()
                .payMethod(freepassDTO.getPayMethod())
                .price(unitPrice)
                .build();
        payService.setOne(payInfoDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/realthink")
    public ResponseEntity<?> realthinkCreate(RealThinkInDTO realThinkDTO){
        PayInfoDTO payInfoDTO = PayInfoDTO.RealThinkBuilder()
                .payMethod(realThinkDTO.getPayMethod())
                .bdSeq(realThinkDTO.getBdSeq())
                .price(1000)//TODO:가격설정
                .comments(realThinkDTO.getBuyerComments())
                .score(realThinkDTO.getBuyerScore())
                .build();
        payService.setOne(payInfoDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
