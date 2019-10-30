package skhu.ht.hotthink.api.idea.model.page;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.IdeaState;
import skhu.ht.hotthink.api.idea.model.page.Pagination;

@Data
public class IdeaPagination extends Pagination {
    IdeaState type;
}
