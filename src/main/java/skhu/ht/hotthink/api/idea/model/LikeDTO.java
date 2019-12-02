package skhu.ht.hotthink.api.idea.model;

import lombok.Builder;
import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.BoardType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
public class LikeDTO {
    private Long seq;//bdSeq, rpSeq 모두 될 수 있음
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    @Builder
    public LikeDTO(Long seq, BoardType boardType) {
        this.seq = seq;
        this.boardType = boardType;
    }
}
