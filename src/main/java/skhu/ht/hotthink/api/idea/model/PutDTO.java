package skhu.ht.hotthink.api.idea.model;

import lombok.*;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.boardin.BoardInDTO;
import skhu.ht.hotthink.api.idea.model.boardin.SubRealInDTO;

import java.util.List;

@Getter
@ToString
@Builder
public class PutDTO {
    private BoardType boardType;
    private Long bdSeq;
    private String title;
    private String contents;
    private SubRealInDTO real;
    private List<AttachDTO> attaches;
}
