package skhu.ht.hotthink.api.idea.model.page;

import lombok.Builder;
import lombok.Getter;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.domain.enums.IdeaState;

@Getter
public class IdeaPagination extends Pagination {
    IdeaState type;
    @Builder(builderClassName = "ideaBuilder", builderMethodName = "ideaBuilder")
    public IdeaPagination(String category, BoardType boardType, int page, int size, int searchBy, String searchText, int orderBy, IdeaState type) {
        super(category, boardType, page, size, searchBy, searchText, orderBy);
        this.type = type;
    }
}
