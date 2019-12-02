package skhu.ht.hotthink.api.idea.model.boardin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RealInDTO extends BoardInDTO {
    private SubRealInDTO real;
}