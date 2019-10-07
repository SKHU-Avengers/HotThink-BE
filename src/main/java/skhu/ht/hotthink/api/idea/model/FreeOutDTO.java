package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.User;

import java.util.Date;

@Data
public class FreeOutDTO {
    private String category;
    private Long seq;
    private int hits;
    private String title;
    private Date date;
    private String contents;
    private int good;
    private String image;
    private UserOutDTO user;
}