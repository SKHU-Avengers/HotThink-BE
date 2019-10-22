package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.IdeaState;

import java.util.Date;

@Data
public class RealOutDTO {
    private Long idSeq;
    private Long seq;
    private Integer hits;
    private String title;
    private IdeaState state;
    private Integer myScore;
    private Integer sellerScore;
    private Date createAt;
    private Date updateAt;
    private String contents;
    private String review;
    //private String pmaterial;//보안상 따로 구현예정

    private Category category;
    private UserOutDTO user;
}
