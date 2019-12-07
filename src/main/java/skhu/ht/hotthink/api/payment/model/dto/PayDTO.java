package skhu.ht.hotthink.api.payment.model.dto;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.PayMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class PayDTO {
    @Enumerated(EnumType.STRING)
    protected PayMethod payMethod;
}
