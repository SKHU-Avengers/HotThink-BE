package skhu.ht.hotthink.api.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import skhu.ht.hotthink.api.payment.model.ReputationType;

@Data
public class ReputationDTO {
    private Long bdSeq;
    private String comments;
    private Integer score;
    @JsonIgnore
    private ReputationType reputationType;
}
