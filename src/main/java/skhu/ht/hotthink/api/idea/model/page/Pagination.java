package skhu.ht.hotthink.api.idea.model.page;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;
import skhu.ht.hotthink.api.domain.enums.BoardType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Null;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
public class Pagination {
    String category;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    BoardType boardType;
    int page;
    int size;
    int searchBy;
    @Nullable
    String searchText;
    int orderBy;
    @Builder
    public Pagination(String category, BoardType boardType, int page, int size, int searchBy, @Nullable String searchText, int orderBy) {
        this.category = category;
        this.boardType = boardType;
        this.page = page;
        this.size = size;
        this.searchBy = searchBy;
        this.searchText = searchText;
        this.orderBy = orderBy;
    }
}
