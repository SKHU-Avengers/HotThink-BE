package skhu.ht.hotthink.api.payment.model.dto;

import lombok.*;
import skhu.ht.hotthink.api.domain.enums.PayCategory;
import skhu.ht.hotthink.api.domain.enums.PayMethod;
import skhu.ht.hotthink.api.payment.model.Period;
import skhu.ht.hotthink.api.payment.model.dto.PayDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayInfoDTO extends PayDTO {
    private PayCategory payCategory;
    private Integer price;
    private Integer buyerScore;
    private String buyerComments;
    @Enumerated(EnumType.STRING)
    private Period period;
    private Long bdSeq;

    @Builder(builderMethodName = "FreePassBuilder",builderClassName = "FreePassBuilder")
    public PayInfoDTO(Integer price, PayMethod payMethod) {
        this.price = price;
        this.payCategory = PayCategory.FREEPASS;
        this.payMethod = payMethod;
    }


    @Builder(builderMethodName = "SubscribeBuilder",builderClassName = "SubscribeBuilder")
    public PayInfoDTO(Integer price, Period period, PayMethod payMethod) {
        this.price = price;
        this.period = period;
        this.payCategory = PayCategory.SUBSCRIBE;
        this.payMethod = payMethod;
    }

    @Builder(builderMethodName = "RealThinkBuilder",builderClassName = "RealThinkBuilder")
    public PayInfoDTO(Integer price, PayMethod payMethod, Long bdSeq, int score, String comments) {
        this.price = price;
        this.payMethod = payMethod;
        this.payCategory = PayCategory.REALTHINK;
        this.bdSeq = bdSeq;
        this.buyerScore = score;
        this.buyerComments = comments;
    }
}
