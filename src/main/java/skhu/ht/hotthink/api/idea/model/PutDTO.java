package skhu.ht.hotthink.api.idea.model;

import lombok.Builder;
import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.boardin.BoardInDTO;
import skhu.ht.hotthink.api.idea.model.boardin.SubRealInDTO;

@Builder
@Data
public class PutDTO {
    private BoardType boardType;
    private Long bdSeq;
    private String title;
    private String contents;
    private String image;
    private SubRealInDTO real;
}
