package skhu.ht.hotthink.api.idea.model.page;

import org.springframework.data.domain.Sort;

public enum PageOrder {
    최근_글(Sort.Direction.DESC, "bdSeq"),
    오래된_글(Sort.Direction.ASC, "bdSeq"),
    생성날짜(Sort.Direction.ASC, "createAt");

    private Sort.Direction direction;
    private String field;

    PageOrder(Sort.Direction direction, String field){
        this.direction = direction;
        this.field = field;
    }

    public Sort of(PageOrder pageOrder){
        return new Sort(pageOrder.direction, pageOrder.field);
    }

    public Sort getSort(){
        return new Sort(this.direction,this.field);
    }


}
