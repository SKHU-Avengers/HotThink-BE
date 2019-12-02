package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.AttachDTO;
import skhu.ht.hotthink.api.idea.model.HistoryOutDTO;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

import java.util.Date;
import java.util.List;

@Data
public class BoardListDTO {
    private Long bdSeq;
    private Long seq;
    private String title;
    private String contents;
    private Date createAt;
    private UserOutDTO user;
    private String category;
    private List<HistoryOutDTO> histories;
    private List<AttachDTO> attaches;
}
