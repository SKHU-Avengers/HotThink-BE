package skhu.ht.hotthink.api.idea.model;

import skhu.ht.hotthink.api.domain.IdeaState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class RealDataDTO {
    private Long seq;
    @Enumerated(EnumType.STRING)
    private IdeaState state;
    private Integer myScore;
    private Integer sellerScore;

    private Date updateAt;
    private String review;
}
