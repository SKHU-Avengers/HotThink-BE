package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.IdeaState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class RealListDTO {
    private Long id_seq;
    private Long seq;
    private Integer hits;
    private String title;
    @Enumerated(EnumType.STRING)
    private IdeaState state;
    private Integer myScore;
    private Integer sellerScore;
    private Date createAt;
    private String contents;
    private String review;
    private String pmaterial;
    private String category;
    private UserOutDTO user;
}
