package skhu.ht.hotthink.api.idea.model.page;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.exception.IdeaInvalidException;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public class Pagination {
    @Getter
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    BoardType boardType;

    @Getter
    String category;

    @Getter
    int page;

    @Getter
    int size;

    @Getter
    PageSearch searchBy;
    PageOrder orderBy;

    @Getter
    @Nullable
    String searchText;

    @Builder
    public Pagination(String category, BoardType boardType, int page, int size, int searchBy, @Nullable String searchText, int orderBy) {
        if(page <= 0 || size <= 0){
                throw new IdeaInvalidException("잘못된 페이지 값 입력");
        }
        this.category = category;
        this.boardType = boardType;
        this.page = page;
        this.size = size;
        switch(searchBy){
            case 0:
                this.searchBy = PageSearch.검색없음;
                break;
            case 1:
                this.searchBy = PageSearch.작성자_닉네임;
                break;
            case 2:
                this.searchBy = PageSearch.제목;
                break;
            case 3:
                this.searchBy = PageSearch.내용;
                break;
        }
        switch(orderBy){
            case 0:
                this.orderBy = PageOrder.최근_글;
                break;
            case 1:
                this.orderBy = PageOrder.오래된_글;
                break;
            case 2:
                this.orderBy = PageOrder.생성날짜;
                break;
        }
        this.searchText = searchText;
    }

    public Sort getOrderBy() {
        return this.orderBy.getSort();
    }
}
