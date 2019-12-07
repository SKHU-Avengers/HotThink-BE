package skhu.ht.hotthink.api.idea.model.boardout;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.LikeOutDTO;
import skhu.ht.hotthink.api.idea.model.reply.ReplyOutDTO;

import java.util.List;

@Data
public class FreeOutDTO extends BoardOutDTO {
    private Integer hits;
    private Integer like;
    private List<ReplyOutDTO> replies;
    private List<LikeOutDTO> likes;
}