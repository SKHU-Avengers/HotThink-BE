package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.Free;
import skhu.ht.hotthink.api.idea.model.Option;
import skhu.ht.hotthink.api.idea.model.Pagination;

import java.util.List;

@Repository
public interface FreeRepository extends JpaRepository<Free, Integer> {
    Option[] searchBy = { new Option(0,"검색없음"), new Option(1,"글쓴이"),
                          new Option(2,"제목"), new Option(3,"내용")};
    Option[] orderBy = { new Option(0,"최근 글"), new Option(1, "오래된 글"),
                         new Option(2, "글쓴이")};
    Sort[] sort = { new Sort(Sort.Direction.DESC, "id"),
             new Sort(Sort.Direction.ASC, "id"),
             new Sort(Sort.Direction.ASC, "user_name")};


    default List<Free> findAll(Pagination pagination){
        Pageable pageable = PageRequest.of(pagination.getPage()-1, pagination.getSize(), sort[pagination.getOrderBy()]);
        Page<Free> page;
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
    @Query(value = "SELECT getFR_Seq(?1)", nativeQuery = true)
    Long findFreeSeq(String category);
    Page<Free> findByCategoryAndContents(String category, String Contents, Pageable pageable);
    Page<Free> findByCategoryAndTitle(String category, String Title, Pageable pageable);
    Page<Free> findByCategoryAndUser(String category, String User, Pageable pageable);
    Page<Free> findByCategory(String category, Pageable pageable);
    Free findFreeBySeqAndCategory(Long seq, Category category);
}