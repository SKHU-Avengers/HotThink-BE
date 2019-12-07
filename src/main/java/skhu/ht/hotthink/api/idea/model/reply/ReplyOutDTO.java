package skhu.ht.hotthink.api.idea.model.reply;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skhu.ht.hotthink.api.domain.enums.ReplyAdopt;
import skhu.ht.hotthink.api.idea.model.LikeOutDTO;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReplyOutDTO extends ReplyPutDTO {
    private Long rpSeq;
    private Date at;
    private String contents;
    @Enumerated(EnumType.STRING)
    private ReplyAdopt adopt;
    private UserOutDTO user;
    private Integer depth;
    private List<ReplyOutDTO> subReplies;
    private List<LikeOutDTO> likes;
}


