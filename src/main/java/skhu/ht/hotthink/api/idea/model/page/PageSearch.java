package skhu.ht.hotthink.api.idea.model.page;

public enum PageSearch {
    검색없음("검색없음"),
    작성자_닉네임("작성자(닉네임)"),
    제목("제목"),
    내용("내용");
    private String option;

    PageSearch(String option){
        this.option = option;
    }
}
