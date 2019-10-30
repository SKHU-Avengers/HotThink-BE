package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

import java.util.Date;

@Data
public class BoardListDTO {
    private Long bdSeq;
    private Long seq;
    private String title;
    private Date createAt;
    private String image;
    private UserOutDTO user;
    private String category;
}
