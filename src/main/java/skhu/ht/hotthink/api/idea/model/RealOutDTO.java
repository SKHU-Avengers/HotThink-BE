package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.BoardType;
import skhu.ht.hotthink.api.domain.Category;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class RealOutDTO {
    private Long idSeq;
    private Long seq;
    private Integer hits;
    private String title;
    private Date createAt;
    private Date updateAt;
    private String contents;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private RealDataDTO real;
    private Category category;
    private UserOutDTO user;
}
