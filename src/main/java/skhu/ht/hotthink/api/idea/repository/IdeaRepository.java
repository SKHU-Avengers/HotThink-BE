package skhu.ht.hotthink.api.idea.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.Idea;
import skhu.ht.hotthink.api.domain.IdeaState;
import skhu.ht.hotthink.api.idea.model.IdeaPagination;
import skhu.ht.hotthink.api.idea.model.Option;
import skhu.ht.hotthink.api.idea.model.Pagination;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Integer> {
    Option[] searchBy = { new Option(0,"검색없음"), new Option(1,"글쓴이"),
            new Option(2,"제목"), new Option(3,"내용")};
    Option[] orderBy = { new Option(0,"최근 글"), new Option(1, "오래된 글"),
            new Option(2, "글쓴이")};
    Sort[] sort = { new Sort(Sort.Direction.DESC, "id"),
            new Sort(Sort.Direction.ASC, "id"),
            new Sort(Sort.Direction.ASC, "user_name")};


    default List<Idea> findAll(IdeaPagination pagination, Category category){
        Pageable pageable = PageRequest.of(pagination.getPage()-1, pagination.getSize(), sort[pagination.getOrderBy()]);
        Page<Idea> page;
        String searchText = pagination.getSearchText();
        IdeaState type = pagination.getType();
        if(type == IdeaState.HOT) {
            switch (pagination.getSearchBy()) {
                case 1:
                    page = this.findByCategoryAndUserAndState(category, searchText, pageable, type);
                    break;
                case 2:
                    page = this.findByCategoryAndTitleAndState(category, searchText, pageable, type);
                    break;
                case 3:
                    page = this.findByCategoryAndContentsAndState(category, searchText, pageable, type);
                    break;
                default:
                    page = this.findByCategoryAndState(category, pageable, type);
            }
        }else{
            switch (pagination.getSearchBy()) {
                case 1:
                    page = this.findByCategoryAndUserAndStateNot(category, searchText, pageable, IdeaState.HOT);
                    break;
                case 2:
                    page = this.findByCategoryAndTitleAndStateNot(category, searchText, pageable, IdeaState.HOT);
                    break;
                case 3:
                    page = this.findByCategoryAndContentsAndStateNot(category, searchText, pageable, IdeaState.HOT);
                    break;
                default:
                    page = this.findByCategoryAndStateNot(category, pageable, IdeaState.HOT);
            }
        }
        return page.getContent();
    }

    Page<Idea> findByCategoryAndContentsAndStateNot(Category category, String searchText, Pageable pageable, IdeaState hot);
    Page<Idea> findByCategoryAndStateNot(Category category, Pageable pageable, IdeaState hot);
    Page<Idea> findByCategoryAndTitleAndStateNot(Category category, String searchText, Pageable pageable, IdeaState hot);
    Page<Idea> findByCategoryAndUserAndStateNot(Category category, String searchText, Pageable pageable, IdeaState hot);
    Page<Idea> findByCategoryAndState(Category category, Pageable pageable, IdeaState type);
    Page<Idea> findByCategoryAndContentsAndState(Category category, String searchText, Pageable pageable, IdeaState type);
    Page<Idea> findByCategoryAndTitleAndState(Category category, String searchText, Pageable pageable, IdeaState type);
    Page<Idea> findByCategoryAndUserAndState(Category category, String searchText, Pageable pageable, IdeaState type);

    Idea findIdeaByIdSeq(Long seq);
    @Query(value = "SELECT getID_seq(?1)", nativeQuery=true)
    Long findIdeaSeq(String category);
}
