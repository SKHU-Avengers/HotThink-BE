package skhu.ht.hotthink.api.idea.model.boardout;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.IdeaState;

import java.util.Date;

@Data
public class SubRealOutDTO {
    private IdeaState state;
    private Date updateAt;
    private String review;
    private String pMaterial;
}
