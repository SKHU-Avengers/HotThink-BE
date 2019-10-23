package skhu.ht.hotthink.api.idea.model;

import skhu.ht.hotthink.api.domain.BoardType;
import skhu.ht.hotthink.api.domain.Category;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class HotOutDTO {
    private Long id_seq;
    private Long seq;
    private Integer hits;
    private String title;
    private Date createAt;
    private Date updateAt;
    private String contents;
    private String review;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private Category category;
    private UserOutDTO user;
}
