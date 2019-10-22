package skhu.ht.hotthink.api.idea.model;

import skhu.ht.hotthink.api.domain.Category;

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
    private Category category;
    private UserOutDTO user;
}
