package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.LikeOutDTO;
import skhu.ht.hotthink.api.idea.model.reply.ReplyOutDTO;

import java.util.List;

@Data
public class FreeListDTO extends BoardListDTO {
    private Integer hits;
    private List<ReplyOutDTO> replies;
    private List<LikeOutDTO> likes;
}
