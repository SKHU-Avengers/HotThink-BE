package skhu.ht.hotthink.api.idea.model.reply;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

import java.util.Date;

@Data
public class ReplyOutDTO {
    private Long seq;
    private Long rpSeq;
    private Date at;
    private Integer good;
    private String contents;
    private String adopt;
    private UserOutDTO user;
}
