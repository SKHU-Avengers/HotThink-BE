package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import java.util.Date;

@Data
public class FreeListDTO {
    private Long bdSeq;
    private Long seq;
    private Integer hits;
    private String title;
    private Date createAt;
    private String image;
    private UserOutDTO user;
    private String category;
}
