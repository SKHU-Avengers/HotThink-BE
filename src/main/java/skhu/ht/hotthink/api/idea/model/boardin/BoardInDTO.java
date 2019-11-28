package skhu.ht.hotthink.api.idea.model.boardin;

import lombok.Data;
import org.springframework.lang.Nullable;
import skhu.ht.hotthink.api.idea.model.AttachDTO;

import java.util.List;

@Data
public class BoardInDTO {
    private String title;
    private String contents;
    private String image;
    @Nullable
    private List<AttachDTO> attaches;
}
