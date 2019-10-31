package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.IdeaState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class SubRealListDTO {
    @Enumerated(EnumType.STRING)
    private IdeaState state;
    private String review;
}
