package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;
import skhu.ht.hotthink.api.idea.model.reply.ReplyOutDTO;

import java.util.Date;

@Data
public class BoardListDTO {
    private Long bdSeq;
    private Long seq;
    private String title;
    private String contents;
    private Date createAt;
    private String image;
    private UserOutDTO user;
    private String category;
}
