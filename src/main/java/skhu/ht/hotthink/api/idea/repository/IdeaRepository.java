package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Idea;
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


    default List<Idea> findAll(Pagination pagination){
        Pageable pageable = PageRequest.of(pagination.getPage()-1, pagination.getSize(), sort[pagination.getOrderBy()]);
        Page<Idea> page;
        String category = pagination.getCategory();
        String searchText = pagination.getSearchText();
        switch(pagination.getSearchBy()){
            case 1:
                page = this.findByCategoryAndUser(category,searchText, pageable);
                break;
            case 2:
                page = this.findByCategoryAndTitle(category, searchText, pageable);
                break;
            case 3:
                page = this.findByCategoryAndContents(category, searchText, pageable);
                break;
            default:
                page = this.findByCategory(category, pageable);
        }
        return page.getContent();
    }
    Page<Idea> findByCategoryAndContents(String category, String Contents, Pageable pageable);
    Page<Idea> findByCategoryAndTitle(String category, String Title, Pageable pageable);
    Page<Idea> findByCategoryAndUser(String category, String User, Pageable pageable);
    Page<Idea> findByCategory(String category, Pageable pageable);
    Idea findIdeaBySeqAndCategory(int seq, String category);
}
