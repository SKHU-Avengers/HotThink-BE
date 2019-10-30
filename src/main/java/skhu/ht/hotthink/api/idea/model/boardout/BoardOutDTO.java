package skhu.ht.hotthink.api.idea.model.boardout;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.HistoryOutDTO;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;
@Data
public class BoardOutDTO {
    private Long bdSeq;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private Long seq;
    private Date createAt;
    private String image;
    private String title;
    private String contents;
    private String category;
    private UserOutDTO user;
    private List<HistoryOutDTO> histories;
}
