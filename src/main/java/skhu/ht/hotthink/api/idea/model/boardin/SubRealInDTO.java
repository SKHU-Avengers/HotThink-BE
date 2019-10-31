package skhu.ht.hotthink.api.idea.model.boardin;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.IdeaState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class SubRealInDTO {
    @Enumerated(EnumType.STRING)
    private IdeaState state;
    private String review;
    private String pMaterial;
}
