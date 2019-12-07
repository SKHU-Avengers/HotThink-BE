package skhu.ht.hotthink.api.payment.model.dto;

import lombok.Data;
import skhu.ht.hotthink.api.payment.model.Period;
import skhu.ht.hotthink.api.payment.model.dto.PayDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class SubscribeInDTO extends PayDTO {
    @Enumerated(EnumType.STRING)
    private Period period;
}
