package skhu.ht.hotthink.api.idea.model;

import lombok.Builder;
import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.BoardType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@Builder
public class LikeDTO {
    private Long boardId;//bdSeq
    private Long replyId;//각 게시물에 달린 댓글의 seq
    private String nickName;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
}
