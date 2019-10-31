package skhu.ht.hotthink.api.idea.model.boardlist;

import lombok.Data;

@Data
public class FreeListDTO extends BoardListDTO {
    private Integer hits;
    private Integer like;
}
