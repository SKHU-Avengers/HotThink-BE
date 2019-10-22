package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.IdeaState;
@Data
public class IdeaPagination extends Pagination{
    IdeaState type;
}
