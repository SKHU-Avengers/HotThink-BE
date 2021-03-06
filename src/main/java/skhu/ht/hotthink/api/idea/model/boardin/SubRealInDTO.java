package skhu.ht.hotthink.api.idea.model.boardin;

import lombok.Data;
import org.springframework.lang.Nullable;
import skhu.ht.hotthink.api.domain.enums.IdeaState;
import skhu.ht.hotthink.api.idea.model.AttachDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class SubRealInDTO {
    @Enumerated(EnumType.STRING)
    private IdeaState state;
    private String difference;
    private String inventor;
    private String rightHolder;
    private Integer progressRate;
    /*
        문혁이의 요청으로 리얼띵크 데이터 추가.
     */
    private String pMaterial;//핵심데이터
    @Nullable
    private List<AttachDTO> attaches;//핵심데이터
}
