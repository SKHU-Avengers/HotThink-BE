package skhu.ht.hotthink.api.payment.model.dto;

import lombok.Data;
import skhu.ht.hotthink.api.payment.model.dto.PayDTO;

@Data
public class RealThinkInDTO extends PayDTO {
    private Long bdSeq;
    private Integer buyerScore;
    private String buyerComments;
}
