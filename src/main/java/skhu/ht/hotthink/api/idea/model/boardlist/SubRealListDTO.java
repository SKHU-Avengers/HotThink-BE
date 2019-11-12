package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;
import skhu.ht.hotthink.api.domain.Attach;
import skhu.ht.hotthink.api.domain.enums.IdeaState;

import javax.persistence.*;
import java.util.List;

@Data
public class SubRealListDTO {
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
    private List<Attach> attaches;//핵심데이터
}
